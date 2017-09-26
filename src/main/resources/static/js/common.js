function activeTab(id) {
    $("#" + id).addClass('active').siblings().removeClass('active');
}