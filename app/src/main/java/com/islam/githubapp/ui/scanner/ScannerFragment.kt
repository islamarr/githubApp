package com.islam.githubapp.ui.scanner

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import at.nineyards.anyline.core.LicenseException
import com.islam.githubapp.R
import com.islam.githubapp.databinding.FragmentScannerBinding
import com.islam.githubapp.ui.BaseFragment
import io.anyline.AnylineSDK
import io.anyline.camera.CameraController
import io.anyline.camera.CameraOpenListener
import io.anyline.plugin.barcode.BarcodeScanViewPlugin
import io.anyline.view.BaseScanViewConfig
import io.anyline.view.ScanViewPluginConfig


private const val TAG = "ScannerFragment"

class ScannerFragment : BaseFragment<FragmentScannerBinding>(), CameraOpenListener {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentScannerBinding
        get() = FragmentScannerBinding::inflate

    override fun setupOnViewCreated(view: View) {
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        try {
            AnylineSDK.init(getString(R.string.anyline_license_key), requireActivity())
        } catch (e: LicenseException) {
            Log.d(TAG, "setupOnViewCreated: $e")
        }

        try {
            binding.barcodeScanView.init("barcode_view_config.json")
        } catch (e: Exception) {
            Log.d(TAG, "setupOnViewCreated: $e")
        }

        binding.barcodeScanView.setCameraOpenListener(this)

        //binding.barcodeScanView.setBarcodeFormats(BarcodeScanView.BarcodeFormat.QR_CODE, BarcodeScanView.BarcodeFormat.CODE_128);

        val barcodeScanviewPluginConfig =
            ScanViewPluginConfig(requireContext(), "barcode_view_config.json")

        val scanViewPlugin =
            BarcodeScanViewPlugin(requireContext(), barcodeScanviewPluginConfig, "BARCODE")

        val barcodeBaseScanViewConfig =
            BaseScanViewConfig(requireContext(), "barcode_view_config.json")

        binding.barcodeScanView.setScanViewConfig(barcodeBaseScanViewConfig)

        binding.barcodeScanView.scanViewPlugin = scanViewPlugin

        scanViewPlugin.addScanResultListener { result -> Log.d(TAG, "setupOnViewCreated: ${(result.result)}") }

        scanViewPlugin.addScannedBarcodesListener { scanResult ->
            val base64Result: String = scanResult.result[0]?.value.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        binding.barcodeScanView.start()
    }

    override fun onPause() {
        super.onPause()

        binding.barcodeScanView.stop()


        binding.barcodeScanView.releaseCameraInBackground()
    }

    override fun onCameraOpened(cameraController: CameraController?, width: Int, height: Int) {
        //the camera is opened async and this is called when the opening is finished
        Log.d(TAG, "Camera opened successfully. Frame resolution $width x $height")
    }

    override fun onCameraError(e: java.lang.Exception?) {
        //This is called if the camera could not be opened.
        // (e.g. If there is no camera or the permission is denied)
        // This is useful to present an alternative way to enter the required data if no camera exists.
        throw RuntimeException(e)
    }
}