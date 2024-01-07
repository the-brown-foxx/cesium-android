package com.thebrownfoxx.cesium

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.thebrownfoxx.cesium.data.api.HttpRoutes
import com.thebrownfoxx.cesium.data.api.auth.AdminService
import com.thebrownfoxx.cesium.data.api.auth.KtorAdminService
import com.thebrownfoxx.cesium.data.api.newHttpClient
import io.ktor.client.HttpClient

class CesiumApplication: Application() {
    private lateinit var client: HttpClient
    private lateinit var routes: HttpRoutes

    private lateinit var _adminService: AdminService
    val adminService get() = _adminService

    override fun onCreate() {
        super.onCreate()
        client = newHttpClient()
        routes = HttpRoutes(application = this)
        _adminService = KtorAdminService(application = this, client = client, routes = routes)
    }
}

val CreationExtras.application
    get() = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CesiumApplication)