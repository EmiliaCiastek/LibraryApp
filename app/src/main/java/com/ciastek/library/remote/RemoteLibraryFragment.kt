package com.ciastek.library.remote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ciastek.library.R
import kotlinx.android.synthetic.main.fragment_remote_library.*

class RemoteLibraryFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_remote_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        remoteBottomNav.setupWithNavController(getBottomNavController())

        add_new_button.setOnClickListener {
            val action = when (getBottomNavController().currentDestination?.id) {
                R.id.nav_books -> RemoteLibraryFragmentDirections.actionAddNewBook()
                R.id.nav_authors -> RemoteLibraryFragmentDirections.actionAddNewAuthor()
                else -> throw IllegalStateException("You shouldn't be here!")
            }

            requireActivity().findNavController(R.id.main_nav_host_fragment).navigate(action)
        }
    }

    private fun getBottomNavController() =
            requireActivity().findNavController(R.id.remote_nav_host_fragment)
}
