package com.a.hub.ui.base.fragment

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment {

    constructor() : super()
    constructor(layoutId: Int) : super(layoutId)

}