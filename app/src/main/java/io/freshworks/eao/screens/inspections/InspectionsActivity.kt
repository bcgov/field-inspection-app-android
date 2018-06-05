package io.freshworks.eao.screens.inspections

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import io.freshworks.eao.R
import io.freshworks.eao.common.adapters.InspectionsPagerAdapter
import io.freshworks.eao.screens.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_inspections.*
import kotlinx.android.synthetic.main.toolbar_with_settings.view.*


class InspectionsActivity : AppCompatActivity(), InspectionsContract.View, OnMapReadyCallback {

    override lateinit var presenter: InspectionsContract.Presenter
    private lateinit var adapter : InspectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspections)
        toolbar.title.text = "EAO Android"

        mapView.onCreate(savedInstanceState)

        InspectionsPresenter(this)
        presenter.initialize()
    }

    override fun onBackPressed() {}

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun setUpTabs() {
        adapter = InspectionsPagerAdapter(supportFragmentManager, this)
        viewPager.adapter = adapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {}
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageSelected(position: Int) {
                presenter.onPageSelected(position)
                tabs.setScrollPosition(position, 0f, true)
            }
        })


        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}

            override fun onTabSelected(tab: TabLayout.Tab) {
                presenter.onTabSelected(tab.position)
                viewPager.currentItem = tab.position
            }
        })

        tabs.setupWithViewPager(viewPager)
    }

    override fun onMapReady(googleMap: GoogleMap) {
    }

    override fun setUpSettingBtn() {
        toolbar.settingsBtn.setOnClickListener {
            presenter.onSettingsBtnClicked()
        }
    }

    override fun setUpGoogleMap() {
        mapView.getMapAsync { map ->
            val camPos = CameraPosition.Builder()
                    .target(LatLng(48.447326, -123.349773))
                    .zoom(10f)
                    .tilt(70f)
                    .build()
            val camUpd3 = CameraUpdateFactory.newCameraPosition(camPos)
            map.animateCamera(camUpd3)
        }
    }

    override fun reloadSubmittedList() {
        adapter.submittedFragment.reloadList()
    }

    override fun goToSettingsScreen() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

}
