package org.thechance.common.presentation.resources

data class DrawableResources(
    val login: String = "img_login_light.png",
    val dollarSign: String = "ic_dollar_sign.svg",
    val starOutlined: String = "ic_star_light.svg",
    val starHalfFilled: String = "ic_star_half_filled_light.svg",
    val starFilled: String = "ic_star_filled_light.svg",
    val filter: String = "ic_filter_light.svg",
    val dots: String = "horizontal_dots.xml",
    val search: String = "ic_search_light.svg",
    val downloadMark: String = "ic_download_mark.svg",
    val seatOutlined: String = "ic_seat_outlined_light.svg",
    val seatFilled: String = "ic_seat_filled_light.svg",
    val dummyImg: String = "dummy_img.png",
    val beepBeepLogoExpanded: String = "ic_beepbeep_logo_expanded.svg",
    val beepBeepLogo: String = "ic_beepbeep_logo.svg",
    val dropDownArrow: String = "ic_drop_down_arrow_light.svg",
    val logout: String = "ic_logout.svg",
    val close: String = "ic_close.svg",
    val arrowLeft: String = "ic_arrow_left_light.svg",
    val arrowRight: String = "ic_arrow_right_light.svg",
    val overviewFilled: String = "ic_overview_fill.svg",
    val overviewOutlined: String = "ic_overview_outlined_light.svg",
    val taxiFilled: String = "ic_taxi_fill.svg",
    val taxiOutlined: String = "ic_taxi_outlined_light.xml",
    val restaurantFilled: String = "ic_restaurant_fill.svg",
    val restaurantOutlined: String = "ic_restaurant_outlined_light.svg",
    val usersFilled: String = "ic_users_fill.svg",
    val usersOutlined: String = "ic_users_outlined_light.svg",
    val permission: String = "ic_edit.xml",
    val disable: String = "ic_disable.svg",
    val delete: String = "ic_delete.svg"
)

val darkDrawableResource = DrawableResources(
    login = "img_login_dark.png",
    starOutlined = "ic_star_dark.svg",
    starHalfFilled = "ic_star_half_filled_dark.svg",
    starFilled = "ic_star_filled_dark.svg",
    seatOutlined = "ic_seat_outlined_dark.svg",
    seatFilled = "ic_seat_filled_dark.svg",
    arrowLeft = "ic_arrow_left_dark.svg",
    arrowRight = "ic_arrow_right_dark.svg",
    overviewOutlined = "ic_overview_outlined_dark.svg",
    taxiOutlined = "ic_taxi_outlined_dark.xml",
    restaurantOutlined = "ic_restaurant_outlined_dark.svg",
    usersOutlined = "ic_users_outlined_dark.svg",
    filter = "ic_filter_dark.svg",
    dropDownArrow = "ic_drop_down_arrow_dark.svg",
    search = "ic_search_dark.svg",
)

val lightDrawableResource = DrawableResources()