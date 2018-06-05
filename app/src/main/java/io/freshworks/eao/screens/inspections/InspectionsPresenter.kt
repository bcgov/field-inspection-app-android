package io.freshworks.eao.screens.inspections

class InspectionsPresenter (private var view: InspectionsContract.View) : InspectionsContract.Presenter{

    init {
        view.presenter = this
    }

    override fun initialize() {
        view.setUpSettingBtn()
        view.setUpGoogleMap()
        view.setUpTabs()
    }

    override fun onSettingsBtnClicked() {
        view.goToSettingsScreen()
    }

    override fun onPageSelected(position: Int) {
    }

    override fun onTabSelected(position: Int) {
    }
}
