package com.github.watabee.devapp.ui.signin

import android.app.Activity
import android.content.Intent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.snackbar.Snackbar

class SignInFragment : Fragment() {

    private fun openSignInView() {
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(
                listOf(
                    AuthUI.IdpConfig.EmailBuilder().build(),
                    AuthUI.IdpConfig.GoogleBuilder().build()
                )
            )
            .setTheme(R.style.AppTheme_SignIn)
            .build()

        startActivityForResult(intent, REQUEST_CODE_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode != REQUEST_CODE_SIGN_IN) {
            return
        }

        val response = IdpResponse.fromResultIntent(data)

        val message = when {
            resultCode == Activity.RESULT_OK -> getString(R.string.sign_in_succeeded)
            response == null -> getString(R.string.sign_in_cancelled)
            else -> response.error?.message.orEmpty()
        }

        showSnackbar(message)
    }

    private fun showSnackbar(message: String) {
        Snackbar.make(
            requireActivity().findViewById<View>(android.R.id.content),
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    companion object {
        private const val FRAGMENT_TAG = "SignInFragment"
        private const val REQUEST_CODE_SIGN_IN = 100

        fun injectIfNeededIn(manager: FragmentManager) {
            if (findSignInFragment(manager) == null) {
                manager.commit {
                    add(SignInFragment::class.java, null, FRAGMENT_TAG)
                }
                manager.executePendingTransactions()
            }
        }

        private fun findSignInFragment(manager: FragmentManager): SignInFragment? =
            manager.findFragmentByTag(FRAGMENT_TAG) as? SignInFragment

        fun openSignInView(manager: FragmentManager) {
            findSignInFragment(manager)?.openSignInView()
        }
    }
}
