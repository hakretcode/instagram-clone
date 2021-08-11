package com.hakretcode.canhub.cropper

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.activity.result.contract.ActivityResultContract

/**
 * An ActivityResultContract to start an activity that allows the user to crop an image.
 *
 * The activity can be heavily customized by the input CropImageContractOptions.
 *
 * If you do not provide an uri in the input the user will be asked to pick an image before cropping.
 */

class CropImageContract :
    ActivityResultContract<CropImageContractOptions, CropImageView.CropResult>() {

    override fun createIntent(context: Context, input: CropImageContractOptions): Intent {
        input.options.validate()
        return Intent(context, CropImageActivity::class.java).apply {
            val bundle = Bundle()
            bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_SOURCE, input.uri)
            bundle.putParcelable(CropImage.CROP_IMAGE_EXTRA_OPTIONS, input.options)
            putExtra(CropImage.CROP_IMAGE_EXTRA_BUNDLE, bundle)
        }
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): CropImageView.CropResult {
        val result = intent?.getParcelableExtra<Parcelable>(CropImage.CROP_IMAGE_EXTRA_RESULT) as? CropImage.ActivityResult?
        return if (result == null || resultCode == Activity.RESULT_CANCELED) {
            CropImage.CancelledResult
        } else {
            result
        }
    }
}
