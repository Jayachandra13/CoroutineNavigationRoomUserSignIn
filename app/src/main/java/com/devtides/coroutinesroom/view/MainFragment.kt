package com.devtides.coroutinesroom.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.devtides.coroutinesroom.R
import com.devtides.coroutinesroom.model.LoginState
import com.devtides.coroutinesroom.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usernameTV.text = LoginState.user?.username
        signoutBtn.setOnClickListener { onSignout() }
        deleteUserBtn.setOnClickListener { onDelete() }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.signout.observe(this, Observer {
            Toast.makeText(activity, "Signed Out", Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })
        viewModel.userDeleted.observe(this, Observer {

            Toast.makeText(activity, "Signed Out", Toast.LENGTH_SHORT).show()
            goToSignUpScreen()
        })
    }

    private fun goToSignUpScreen(){
        val action: NavDirections = MainFragmentDirections.actionGoToSignup()
        Navigation.findNavController(usernameTV).navigate(action)
    }

    private fun onSignout() {
//        val action = MainFragmentDirections.actionGoToSignup()
//        Navigation.findNavController(usernameTV).navigate(action)
        viewModel.onSignout()
    }

    private fun onDelete() {
//        val action = MainFragmentDirections.actionGoToSignup()
//        Navigation.findNavController(usernameTV).navigate(action)
        activity?.let {
            AlertDialog.Builder(it)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete this user")
                .setPositiveButton("Yes"){p0, p1 -> viewModel.onDeleteUser()}
                .setNegativeButton("Cancel", null)
                .create()
                .show()
        }
    }

}
