package org.hyperskill.simplebankmanager


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.hyperskill.simplebankmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), BankManager {

    lateinit var mainBinding: ActivityMainBinding
    lateinit var username: String
    lateinit var password: String
    var balance: Double = 100.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.extras?.getString("username") ?: "Lara"
        password = intent.extras?.getString("password") ?: "1234"
        balance = intent.extras?.getDouble("balance") ?: 100.0
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)
    }

    override fun isValidLogin(username: String, password: String): Boolean {
        return this.username == username
                && this.password == password
    }

    override fun currentBalance(): Double {
        return balance
    }

    override fun hasFunds(amount: Double): Boolean {
        return balance >= amount
    }

    override fun subtractBalance(amount: Double) {
        balance -= amount
    }

    override fun isValidAccount(account: String): Boolean {
        return "^[sc]a\\d{4}$".toRegex().matches(account)
    }

//    override fun onBackPressed() {
//        //comment next line to test incorrect back button behaviour
//        //super.onBackPressed()
//    }
}