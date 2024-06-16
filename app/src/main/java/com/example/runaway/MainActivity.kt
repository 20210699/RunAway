package com.example.runaway

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.runaway.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    private lateinit var mapView: MapView
    private lateinit var naverMap: NaverMap


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

    }

    //naver map
    override fun onMapReady(p0: NaverMap) {
        TODO("Not yet implemented")
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
            R.id.item_shalter -> {
                Log.d("mobileapp", "대피소 메뉴")
                // val intent = Intent(this, BoardActivity::class.java)
                // startActivity(intent)
                binding.drawerLayout.closeDrawers()
                return true
            }
            R.id.item_save -> {
                Log.d("mobileapp", "저장 메뉴")
                // val intent = Intent(this, BoardActivity::class.java)
                // startActivity(intent)
                binding.drawerLayout.closeDrawers()
                return true
            }
            R.id.item_setting -> {
                Log.d("mobileapp", "설정 메뉴")
                binding.drawerLayout.closeDrawers()
                return true
            }
        }
        return false
    }
}
