package com.example.crud

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.crud.databinding.ActivityMainBinding
import com.example.crud.databinding.ActivityMainBinding.inflate
import java.sql.DatabaseMetaData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val context = this
        var db = DBHelper(context)
        binding.btninsert.setOnClickListener{
            var etname = binding.etname.text.toString()
            var etage = binding.etage.text.toString()
            if (etname.isNotEmpty() && etage.isNotEmpty()) {
                var user = User(etname, etage.toInt())
                db.insertData(user)
            } else {
                Toast.makeText(applicationContext, "Please fill the fields", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnread.setOnClickListener {
            var data = db.readData()
            binding.tvresult.text = ""
            for(i in 0 until data.size) {
                binding.tvresult.append(data.get(i).id.toString() + " "
                + data.get(i).name + " " + data.get(i).age + "\n")
            }
        }

        binding.btnupdate.setOnClickListener {
            db.updateData()
            binding.btnread.performClick()
        }

        binding.btndelete.setOnClickListener {
            db.deleteData()
            binding.btnread.performClick()
        }
    }
}