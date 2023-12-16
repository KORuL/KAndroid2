@file:Suppress("NOTHING_TO_INLINE")

package ru.korul.kandroid2

import android.content.SharedPreferences
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager

inline val Fragment.defaultSharedPreferences: SharedPreferences
    get() = PreferenceManager.getDefaultSharedPreferences(requireContext())

inline fun Fragment.toast(text: CharSequence): Toast = requireActivity().toast(text)

inline fun Fragment.longToast(text: CharSequence): Toast = requireActivity().longToast(text)

inline fun Fragment.toast(@StringRes resId: Int): Toast = requireActivity().toast(resId)

inline fun Fragment.longToast(@StringRes resId: Int): Toast = requireActivity().longToast(resId)

inline fun <reified T : Preference> PreferenceFragmentCompat.findPref(key: String): T? = findPreference(key) as T?
