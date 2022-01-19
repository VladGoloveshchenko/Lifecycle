package com.example.lifecycle

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.lifecycle.databinding.ActivityContainerBinding
import com.example.lifecycle.fragment.RandomFragment
import java.util.*

interface Navigator {
    fun pushNext()
}

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

class ContainerActivity : AppCompatActivity(), Navigator {

    private lateinit var binding: ActivityContainerBinding

    private val fragmentListener: FragmentManager.FragmentLifecycleCallbacks =
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                update()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContainerBinding.inflate(layoutInflater).also { setContentView(it.root) }

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.container, createFragment())
                .addToBackStack(null)
                .commit()
        }

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
        super.onDestroy()
    }

    override fun pushNext() {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, createFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun update() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        if (currentFragment is RandomFragment) {
            binding.currentFragmentUuidTextView.text = currentFragment.getUuid()
        } else {
            binding.currentFragmentUuidTextView.text = ""
        }
        if (currentFragment is RandomFragment) {
            currentFragment.onNewScreenNumber(supportFragmentManager.backStackEntryCount)
        }
    }

    private fun createFragment() =
        RandomFragment.newInstance(UUID.randomUUID().toString())
}