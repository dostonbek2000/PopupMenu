package com.dostonbek.popupmenu

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dostonbek.popupmenu.databinding.ActivityMainBinding
import com.dostonbek.popupmenu.databinding.AddDialogBinding
import com.dostonbek.popupmenu.databinding.ItemUserBinding
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var list=ArrayList<UserData>()
    private lateinit var adapter: RvAdapter
    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        list = ArrayList()
        adapter = RvAdapter(this, list)
        binding.recyclerview.adapter = adapter
        binding.addBtn.setOnClickListener {


            val inflter = LayoutInflater.from(this)
            val v = inflter.inflate(R.layout.add_dialog, null)

            /**set view*/

            val alertDialog = AlertDialog.Builder(this)

            alertDialog.setView(v)
            alertDialog.setPositiveButton("Ok") { dialog, _ ->
                val name = v.findViewById<TextInputEditText>(R.id.d_name)
                val surname = v.findViewById<TextInputEditText>(R.id.d_surname)
                list.add(UserData(" ${name.text}", "${surname.text}"))
                adapter.notifyDataSetChanged()
                Toast.makeText(this, "Adding User Information Success", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
            alertDialog.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show()

            }
            alertDialog.create()
            alertDialog.show()
        }
    }


}