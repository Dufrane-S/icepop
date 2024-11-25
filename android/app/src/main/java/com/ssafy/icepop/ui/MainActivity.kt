package com.ssafy.icepop.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Typeface
import android.nfc.NfcAdapter
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.ssafy.icepop.R
import com.ssafy.icepop.data.model.dto.request.TokenRequest
import com.ssafy.icepop.data.remote.RetrofitUtil
import com.ssafy.icepop.databinding.ActivityMainBinding
import com.ssafy.icepop.ui.auth.AuthActivity
import com.ssafy.icepop.ui.home.HomeFragment
import com.ssafy.icepop.ui.list.IceCreamDetailFragment
import com.ssafy.icepop.ui.list.IceCreamListFragment
import com.ssafy.icepop.ui.list.IceCreamOrderFragment
import com.ssafy.icepop.ui.my.MyPageFragment
import com.ssafy.icepop.ui.order.OrderDetailFragment
import com.ssafy.icepop.ui.order.OrderListFragment
import com.ssafy.icepop.ui.order.OrderReviewFragment
import com.ssafy.smartstore_jetpack.base.ApplicationClass
import com.ssafy.smartstore_jetpack.base.BaseActivity
import com.ssafy.smartstore_jetpack.util.PermissionChecker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.bootpay.android.BootpayAnalytics
import org.altbeacon.beacon.Beacon
import org.altbeacon.beacon.BeaconManager
import org.altbeacon.beacon.BeaconParser
import org.altbeacon.beacon.Identifier
import org.altbeacon.beacon.MonitorNotifier
import org.altbeacon.beacon.RangeNotifier
import org.altbeacon.beacon.Region

private const val TAG = "MainActivity_ssafy"
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val activityViewModel: ActivityViewModel by viewModels()

    private lateinit var nAdapter: NfcAdapter
    private lateinit var pIntent: PendingIntent
    private lateinit var filters: Array<IntentFilter>

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var beaconManager: BeaconManager

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private var lastRunTime : Long = 0

    private val region = Region(
        "estimote",
        listOf(
            Identifier.parse(BEACON_UUID),
            Identifier.parse(BEACON_MAJOR),
            Identifier.parse(BEACON_MINOR)),
        BLUETOOTH_ADDRESS
    )

    //모니터링 결과를 처리할 Notifier를 지정.
    // region에 해당하는 beacon 유무 판단
    var monitorNotifier: MonitorNotifier = object : MonitorNotifier {
        override fun didEnterRegion(region: Region) { //발견 함.
            Log.d(TAG, "I just saw an beacon for the first time!")
        }

        override fun didExitRegion(region: Region) { //발견 못함.
            Log.d(TAG, "I no longer see an beacon")
        }

        override fun didDetermineStateForRegion(state: Int, region: Region) { //상태변경
            Log.d(TAG, "I have just switched from seeing/not seeing beacons: $state")
        }
    }

    //매초마다 해당 리전의 beacon 정보들을 collection으로 제공받아 처리한다.
    var rangeNotifier: RangeNotifier = object : RangeNotifier {
        override fun didRangeBeaconsInRegion(beacons: MutableCollection<Beacon>?, r: Region?) {
            beacons?.run{
                if (isNotEmpty()) {
                    forEach { beacon ->
                        val msg = "distance: " + beacon.distance
                        // 사정거리 내에 있을 경우 이벤트 표시 다이얼로그 팝업
                        if (beacon.distance <= BEACON_DISTANCE) {
                            Log.d(TAG, "didRangeBeaconsInRegion: 5m distance 이내.")

                            handleDialog()
                        } else {
                            Log.d(TAG, "didRangeBeaconsInRegion: distance 이외.")
                        }
                        Log.d( TAG,"distance: " + beacon.distance + " id:" + beacon.id1 + "/" + beacon.id2 + "/" + beacon.id3)
                    }
                }
                if (isEmpty()) {
                    Log.d(TAG, "didRangeBeaconsInRegion: 비컨을 찾을 수 없습니다.")
                }
            }
        }
    }

    private val checker = PermissionChecker(this)
    private val runtimePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        arrayOf(
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.BLUETOOTH_ADVERTISE,
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    } else {
        arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    private val notificationPermission = arrayOf(Manifest.permission.POST_NOTIFICATIONS)

    //블루투스 권한 요청하는 activity
    private val requestBLEActivity: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult() ){
        // 사용자의 블루투스 사용이 가능한지 확인
        if (bluetoothAdapter.isEnabled) {
            startScan()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        BootpayAnalytics.init(this, ApplicationClass.APPLICATION_ID)

        lastRunTime = ApplicationClass.sharedPreferencesUtil.getLastRunTime()

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_layout, HomeFragment())
            .commit()

        checkNotificationPermission()
        initListener()

        // 뒤로 가기 버튼을 오버라이드하여 첫 번째 탭으로 이동
        onBackPressedDispatcher.addCallback(this) {
            if (binding.bottomNavigation.visibility == View.GONE) {
                val className = supportFragmentManager.findFragmentById(R.id.main_fragment_layout)?.javaClass?.simpleName

                val listItemClicked = activityViewModel.orderItemClick.value!!

                if (listItemClicked && className == "OrderDetailFragment") {
                    activityViewModel.resetOrderItemClick()
                    openFragment(ORDER_LIST_FRAGMENT)
                }

                if (!listItemClicked && className =="OrderDetailFragment") {
                    openFragment(MY_PAGE_FRAGMENT)
                }

                when (className) {
                    "IceCreamDetailFragment" -> openFragment(ICE_CREAM_LIST_FRAGMENT)
                    "IceCreamOrderFragment" -> openFragment(ICE_CREAM_LIST_FRAGMENT)
                    "OrderListFragment" -> openFragment(MY_PAGE_FRAGMENT)
                    "OrderReviewFragment" -> openFragment(ORDER_DETAIL_FRAGMENT)
                }

                Log.d(TAG, "onCreate: ${supportFragmentManager.findFragmentById(R.id.main_fragment_layout)?.javaClass?.simpleName}")
                Log.d(TAG, "onCreate: ${R.layout.fragment_ice_cream_detail}")
            }
        }

    }

    override fun onResume() {
        super.onResume()

        setBeacon()

        //nfc 대기
        initNfc()
        nAdapter.enableForegroundDispatch(this, pIntent, filters, null)


        /* permission check */
        if (!checker.checkPermission(this, runtimePermissions)) {
            checker.setOnGrantedListener{
                //퍼미션 획득 성공일때
                startScan()
            }

            checker.requestPermissionLauncher.launch(runtimePermissions)
        } else { //이미 전체 권한이 있는 경우
            startScan()
        }
    }

    override fun onPause() {
        super.onPause()
        nAdapter.disableForegroundDispatch(this)

        beaconManager.stopMonitoring(region)
        beaconManager.stopRangingBeacons(region)
    }

    private fun checkNotificationPermission() {
        if (!checker.checkPermission(this, notificationPermission)) {
            //권한 획득시
            checker.setOnGrantedListener {
                init()
            }

            checker.requestPermissionLauncher.launch(notificationPermission)  // 권한 없으면 창 띄우기
        }
        //이미 전체 권한이 있는 경우,
        else {
            init()
        }
    }

    private fun init(){
        initFCM()
        createNotificationChannel(channel_id, "ssafy")
    }

    private fun initFCM() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "FCM 토큰 얻기에 실패하였습니다.", task.exception)
                return@OnCompleteListener
            }

            // token log 남기기
            Log.d(TAG, "token: ${task.result?:"task.result is null"}")

            //토큰을 서버에 업로드
            if(task.result != null){
                uploadToken(task.result!!)
            }
        })
    }

    // Notification 수신을 위한 체널 추가
    private fun createNotificationChannel(id: String, name: String){
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationManager: NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel(id, name, importance))
        }
    }

    private fun initListener() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_layout, HomeFragment())
                        .commit()
                    true
                }

                R.id.list_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_layout, IceCreamListFragment())
                        .commit()
                    true
                }

                R.id.my_page -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_layout, MyPageFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.setOnItemReselectedListener { item ->
            // 재 선택시 다시 랜더링 방지
            if (binding.bottomNavigation.selectedItemId != item.itemId) {
                binding.bottomNavigation.selectedItemId = item.itemId
            }
        }
    }

    fun openFragment(index: Int, key: String, value: Int) {
        moveFragment(index, key, value)
    }

    fun openFragment(index: Int, value: Int) {
        moveFragment(index, "", value)
    }

    fun openFragment(index: Int) {
        moveFragment(index, "", 0)
    }

    private fun moveFragment(index: Int, key: String, value: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        when (index) {
            ICE_CREAM_DETAIL_FRAGMENT -> {
                val fragment = IceCreamDetailFragment.newInstance(value)
                transaction.replace(R.id.main_fragment_layout, fragment)
//                    .addToBackStack(null)
            }
            ICE_CREAM_ORDER_FRAGMENT -> {
                transaction.replace(R.id.main_fragment_layout, IceCreamOrderFragment())
//                    .addToBackStack(null)
            }
            ICE_CREAM_LIST_FRAGMENT -> {
//                supportFragmentManager.popBackStack()
                transaction.replace(R.id.main_fragment_layout, IceCreamListFragment())
            }
            ORDER_LIST_FRAGMENT -> {
                transaction.replace(R.id.main_fragment_layout, OrderListFragment())
//                    .addToBackStack(null)
            }
            ORDER_DETAIL_FRAGMENT -> {
                val fragment = OrderDetailFragment.newInstance(value)
                transaction.replace(R.id.main_fragment_layout, fragment)
//                    .addToBackStack(null)
            }
            ORDER_REVIEW_FRAGMENT -> {
                val fragment = OrderReviewFragment.newInstance(value)
                transaction.replace(R.id.main_fragment_layout, fragment)
//                    .addToBackStack(null)
            }
            HOME_FRAGMENT -> {
                transaction.replace(R.id.main_fragment_layout, HomeFragment())
                binding.bottomNavigation.selectedItemId = R.id.home_page
            }
            MY_PAGE_FRAGMENT -> {
                transaction.replace(R.id.main_fragment_layout, MyPageFragment())
            }
            AUTH_ACTIVITY -> {
                val intent = Intent(this, AuthActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent)
            }
        }
        transaction.commit()
    }

    fun hideBottomNav(state: Boolean) {
        if (state) binding.bottomNavigation.visibility = View.GONE
        else binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun setBeacon() {
        //BeaconManager 지정
        beaconManager = BeaconManager.getInstanceForApplication(this)

        beaconManager.beaconParsers.add(BeaconParser().setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"))
        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter
    }

    private fun startScan() {
        if ( !bluetoothAdapter.isEnabled ) {
            requestEnableBLE()
        }

        // 리전에 비컨이 있는지 없는지..정보를 받는 클래스 지정
        beaconManager.addMonitorNotifier(monitorNotifier)
        beaconManager.startMonitoring(region)

        //detacting되는 해당 region의 beacon정보를 받는 클래스 지정.
        beaconManager.addRangeNotifier(rangeNotifier)
        beaconManager.startRangingBeacons(region)
    }

    private fun requestEnableBLE(){
        val callBLEEnableIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestBLEActivity.launch(callBLEEnableIntent)
        Log.d(TAG, "requestEnableBLE: ")
    }

    fun initNfc() {
        nAdapter = NfcAdapter.getDefaultAdapter(this)
        nAdapter.disableForegroundDispatch(this)
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_SINGLE_TOP // 내가 top일 때 재사용 onNewintent
        }
        pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_MUTABLE)


        val filter = IntentFilter()
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
        filter.addCategory(Intent.CATEGORY_DEFAULT)
        filter.addDataType("text/*")
        filters = arrayOf(filter)
    }

    private fun handleDialog() {
//        val oneDayMillis = 24 * 60 * 60 * 1000L
        val oneDayMillis = 1000L

        if (System.currentTimeMillis() > lastRunTime + oneDayMillis) {
            Log.d(TAG, "handleDialog: inner")

            showStoreEventDialog()

            ApplicationClass.sharedPreferencesUtil.saveCurrentTime(System.currentTimeMillis())
        }

        beaconManager.stopMonitoring(region)
        beaconManager.stopRangingBeacons(region)
    }

    private fun showStoreEventDialog() {
        val builder = AlertDialog.Builder(this, R.style.AppAlertDialogTheme)
            .setTitle("아이스크림 할인 이벤트!")
            .setMessage("\uD83C\uDF89 특별한 할인 이벤트가 시작되었습니다! \uD83C\uDF89 \n" +
                    "다양한 메뉴와 더 큰 혜택을 함께 즐기실 수 있는 이 기회를 놓치지 마세요!\n" +
                    "목록에서 원하는 아이스크림을 찾아보시고, 할인된 가격으로 맛있게 즐겨보세요. \uD83D\uDE0B \n" +
                    "목록에서 살펴보아요!!"
            )
            .setPositiveButton("확인") { dialog, _ ->
            //comment를 추가하는 코드
            // 확인 버튼 클릭 시 처리할 코드
            dialog.dismiss()
        }

        // AlertDialog 생성 및 표시
        val dialog = builder.create()

        dialog.window?.setBackgroundDrawableResource(R.drawable.dialog_background)

        dialog.show()

        val title = dialog.findViewById<TextView>(android.R.id.title)
        val textView = dialog.findViewById<TextView>(android.R.id.message)
        val face = ResourcesCompat.getFont(this, R.font.meet_me)
        val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)

        val customFont = ResourcesCompat.getFont(this, R.font.meet_me)  // meet_me는 custom font 파일

        positiveButton.setTextColor(getColor(R.color.primary_color))
        positiveButton.typeface = customFont

        textView?.typeface = face

        title?.textSize = 24f
        textView?.textSize = 18f
    }

    companion object {
        const val ICE_CREAM_DETAIL_FRAGMENT = 1
        const val ICE_CREAM_ORDER_FRAGMENT = 2
        const val ICE_CREAM_LIST_FRAGMENT = 3
        const val ORDER_LIST_FRAGMENT = 4
        const val ORDER_DETAIL_FRAGMENT = 5
        const val ORDER_REVIEW_FRAGMENT = 6
        const val HOME_FRAGMENT = 7
        const val MY_PAGE_FRAGMENT = 8
        const val AUTH_ACTIVITY = 9

        private const val BEACON_UUID = "e2c56db5-dffb-48d2-b060-d0f5a71096e0" // 우리반 모두 동일값
        private const val BEACON_MAJOR = "40011" // 우리반 모두 동일값
        private const val BEACON_MINOR = "43431" // 우리반 모두 동일값
        private const val BLUETOOTH_ADDRESS = "C3:00:00:1C:5E:86"
        private const val BEACON_DISTANCE = 5.0 // 거리

        const val channel_id = "ice_creamapp_channel"

        fun uploadToken(token: String) {
            val email = ApplicationClass.sharedPreferencesUtil.getUser().email

            CoroutineScope(Dispatchers.IO).launch {
                runCatching {
                    RetrofitUtil.userService.registerFcmToken(TokenRequest(email, token))
                }.onSuccess {
                    Log.d(TAG, "uploadToken: it")
                }.onFailure {
                    Log.d(TAG, "uploadToken: 실패")
                }
            }
        }
    }
}