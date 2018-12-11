package com.etecartur.esdras.suggestionboxkotlin

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.etecartur.esdras.suggestionboxkotlin.adapter.SuggestionAdapter
import com.etecartur.esdras.suggestionboxkotlin.model.Suggestion
import kotlinx.android.synthetic.main.activity_suggestion_list.*
import org.json.JSONException
import org.json.JSONObject

class SuggestionListActivity : AppCompatActivity() {

    val url = "https://esdras.000webhostapp.com/getjson.php"

    //ListView para preenchimento de dados
    lateinit var listSuggestion: ArrayList<Suggestion>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion_list)

        var requestQueue: RequestQueue = Volley.newRequestQueue(this)

        listSuggestion = arrayListOf<Suggestion>()

        val dialog = ProgressDialog(this)
            dialog.setTitle("Sugestões")
            dialog.setMessage("Recureando informações no banco de dados, por favor aguarde...")
            dialog.show()


        //Criando StringRequest - Volley
        val stringRequest = StringRequest(Request.Method.GET, url, Response.Listener { response ->
            try {
                val jsonObject = JSONObject(response)
                val jsonArray = jsonObject.getJSONArray("sugestoes")

                for (i in 0 until jsonArray.length()) {
                    val suggestionObject = jsonArray.getJSONObject(i)
                    val suggestion = Suggestion(suggestionObject.getString("id"), suggestionObject.getString("objetivo"),
                            suggestionObject.getString("descricao"), suggestionObject.getString("aluno"),
                            suggestionObject.getString("curso"), suggestionObject.getString("data"))
                    listSuggestion.add(suggestion)
                }
                Log.d("ListActivity: ", listSuggestion.toString())
                val adapter = SuggestionAdapter(applicationContext, listSuggestion)
                listViewSuggestionBasic.setAdapter(adapter)
                adapter.notifyDataSetChanged()
                dialog.dismiss()

            } catch (e: JSONException) {
                Toast.makeText(applicationContext, "Exception: " + e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }, Response.ErrorListener {
            error ->
            Log.d("Error: ",error.message)
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()
        })



        requestQueue.add(stringRequest)

    }
}
