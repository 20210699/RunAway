package com.example.runaway

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.util.TypedValue
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.runaway.databinding.ActivityBottomSheetBinding
import com.example.runaway.databinding.ActivityMainBinding
import com.example.runaway.databinding.ItemMainBinding
import com.example.runaway.databinding.NavigationHeaderBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.navigation.NavigationView
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationSource
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap

    lateinit var headerView : View
    lateinit var sharedPreference: SharedPreferences
    private var marker = Marker()

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>

    // naver 현재 위치 구현
    private val LOCATION_PERMISSION_REQUEST_CODE: Int = 1000
    private lateinit var locationSource: FusedLocationSource

    // Naver 마커
    private lateinit var naverMapList: MutableList<MyJsonItems>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.mipmap.ic_launcher)
        supportActionBar?.title = ""  // Toolbar의 타이틀을 비우거나 원하는 문자열로 변경

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navigationView: NavigationView = binding.navView

        // ActionBarDrawerToggle 객체를 멤버 변수로 할당
        actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.drawer_opened,
            R.string.drawer_closed
        )

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()

        // NavigationView 리스너 설정
        navigationView.setNavigationItemSelectedListener(this)

        headerView = binding.navView.getHeaderView(0)
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener {
            Log.d("mobileapp", "button.setOnClickListener")
            val intent = Intent(this, AuthActivity::class.java)
            if (button.text.equals("로그인")){
                intent.putExtra("status","logout")
            } else if(button.text.equals("로그아웃")){
                intent.putExtra("status", "login")
            }
            startActivity(intent)

            binding.drawerLayout.closeDrawers()
        }

        // 현재 위치 구현
        mapView = findViewById(R.id.naver_map)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        locationSource = FusedLocationSource(this,LOCATION_PERMISSION_REQUEST_CODE)

        //bottomsheet 설정
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        // Bottom Sheet 상태 조정
        bottomSheetBehavior.peekHeight = 50
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        val call : Call<JsonResponse> = RetrofitConnection.jsonNetServ.getJsonList(
            "nfpiyRzbLt60+MjNWKX0L0etodMnmVPkRC5w8/IaHOUS3WSW0ZeBn3jow4IQRqHyvON6f2FXBB+Lxfa8G8HQ7Q==",
            1,
            10,
            "json"
        )

        call?.enqueue(object : Callback<JsonResponse> {

            //response 성공
            override fun onResponse(call: Call<JsonResponse>, response: Response<JsonResponse>) {
                val datas = response.body()!!.TsunamiShelter.get(1).row.toMutableList()
                val adapter = JsonAdapter(applicationContext,datas)
                if(response.isSuccessful){
                    Log.d("mobileapp","$response")
                    Log.d("mobileapp","${response.body()}")
                    Log.d("mobileapp","$datas")
                    //Toast.makeText(applicationContext,"불러오기 성공 ${response}", Toast.LENGTH_LONG).show()

                    // 마커 구현
                    val markers = Array(datas.size) { Marker() }

                    for (i in datas.indices) {
                        markers[i] = Marker().apply {
                            val lat = datas[i].lat.toDouble()
                            val lnt = datas[i].lon.toDouble()
                            position = LatLng(lat, lnt)
                            map = naverMap
                            setOnClickListener { overlay ->
                                Toast.makeText(applicationContext, "${datas[i].shel_nm}", Toast.LENGTH_SHORT).show()
                                false
                            }
                        }
                    }

//                    var lat = datas.get(0).lat.toDouble()
//                    var lon = datas.get(0).lon.toDouble()
//
//                    marker.position = LatLng(lat,lon)
//                    marker.map = naverMap

                    //Toast.makeText(applicationContext,"${datas.get(0).address}",Toast.LENGTH_LONG).show()

                    binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                    binding.recyclerView.addItemDecoration(
                        DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
                    )
                    binding.recyclerView.adapter = adapter
                }
            }

            //response 실패
            override fun onFailure(call: Call<JsonResponse>, t: Throwable) {
                Toast.makeText(applicationContext,"불러오기 실패", Toast.LENGTH_LONG).show()
                Log.d("mobileapp", "onFailure $t")
            }
        })

        //검색 기능 구현
        binding.btnSearch.setOnClickListener {
            val name = binding.edtSearch.text
            //추후 구현
        }


        // 설정값 반영
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreference.getString("color", "#00ff00")
        binding.recyclerView.setBackgroundColor(Color.parseColor(color))

        val size = sharedPreference.getString("size", "16.0f")
        binding.edtSearch.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

    }

    override fun onResume() {
        super.onResume()

        // 설정값 반영
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreference.getString("color", "#ffffff")
        binding.recyclerView.setBackgroundColor(Color.parseColor(color))

//        val idStr = sharedPreference.getString("id", "")
//        binding.todoTitle.text = idStr

        val size = sharedPreference.getString("size", "30.0f")
        binding.edtSearch.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())
    }

    //naver map
    override fun onMapReady(naverMap: NaverMap) {
        // 현재 위치 구현
        this.naverMap = naverMap
        naverMap.locationSource = locationSource
        naverMap.locationTrackingMode = LocationTrackingMode.Follow
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    // DrawerLayout Toggle 핸들링
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    // Drawer 메뉴 선택 처리
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_board -> {
                Log.d("mobileapp", "게시판 메뉴")
                 val intent = Intent(this, BoardActivity::class.java)
                 startActivity(intent)
                binding.drawerLayout.closeDrawers()
                return true
            }
            R.id.item_save -> {
                Log.d("mobileapp", "저장 메뉴")
                val intent = Intent(this, SaveListActivity::class.java)
                startActivity(intent)
                binding.drawerLayout.closeDrawers()
                return true
            }
            R.id.item_setting -> {
                Log.d("mobileapp", "설정 메뉴")
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                binding.drawerLayout.closeDrawers()
                return true
            }
            R.id.item_memo -> {
                Log.d("mobileapp", "저장 메뉴")
                val intent = Intent(this, MemoActivity::class.java)
                startActivity(intent)
                binding.drawerLayout.closeDrawers()
                return true
            }
        }
        return false
    }

    override fun onStart(){
        super.onStart()
        val button = headerView.findViewById<Button>(R.id.btnAuth)
        val tv = headerView.findViewById<TextView>(R.id.tvID)
        val tvEmail = headerView.findViewById<TextView>(R.id.tvEmail)

        tv.visibility = View.VISIBLE
        tvEmail.visibility = View.VISIBLE

        if(MyApplication.checkAuth()){
            button.text = "로그아웃"
            tv.text = "${MyApplication.name()}"
            tvEmail.text = "${MyApplication.email}"
        } else {
            button.text = "로그인"
            tv.visibility = View.GONE
            tvEmail.visibility = View.GONE
        }
    }


}
