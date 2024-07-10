package com.dostonbek.popupmenu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dostonbek.popupmenu.databinding.ItemUserBinding
import com.google.android.material.textfield.TextInputEditText


class RvAdapter(val context: Context, val list: ArrayList<UserData>) :
    RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(private var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(user: UserData) {
            binding.itemName.text = user.name
            binding.itemSurname.text = user.surname
            binding.popupBtn.setOnClickListener {
                popupMenus(it)
            }


        }

        @SuppressLint("NotifyDataSetChanged")
        private fun popupMenus(v: View) {
            val position = list[adapterPosition]
            val popupMenus = PopupMenu(context, v)
            popupMenus.inflate(R.menu.popup_menu)
            popupMenus.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.menu_edit -> {
                        val v = LayoutInflater.from(context).inflate(R.layout.add_dialog, null)
                        val name = v.findViewById<TextInputEditText>(R.id.d_name)
                        val surname = v.findViewById<TextInputEditText>(R.id.d_surname)
                        AlertDialog.Builder(context).setView(v)
                            .setPositiveButton("Ok") { dialog, _ ->
                                position.name = name.text.toString()
                                position.surname = surname.text.toString()
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context, "User Information is Edited", Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()

                            }.setNegativeButton("Cancel") { dialog, _ ->
                                dialog.dismiss()

                            }.create().show()

                        true
                    }

                    R.id.menu_delete -> {
                        /**set delete*/
                        AlertDialog.Builder(context).setTitle("Delete")
                            .setIcon(R.drawable.baseline_add_24)
                            .setMessage("Are you sure delete this Information")
                            .setPositiveButton("Yes") { dialog, _ ->
                                list.removeAt(adapterPosition)
                                notifyDataSetChanged()
                                Toast.makeText(
                                    context, "Deleted this Information", Toast.LENGTH_SHORT
                                ).show()
                                dialog.dismiss()
                            }.setNegativeButton("No") { dialog, _ ->
                                dialog.dismiss()
                            }.create().show()

                        true
                    }

                    else -> true
                }

            }
            popupMenus.show()
            val popup = PopupMenu::class.java.getDeclaredField("mPopup")
            popup.isAccessible = true
            val menu = popup.get(popupMenus)
            menu.javaClass.getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(menu, true)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }


}