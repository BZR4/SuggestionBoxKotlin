package com.etecartur.esdras.suggestionboxkotlin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONException
import org.json.JSONObject
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*

//TODO - Inserir permissao de acesso a INTERNET

class MainActivity : AppCompatActivity() {

    //TODO - Inserir url de acesso ao banco de dados
    val url = "https://esdras.000webhostapp.com/new_suggestion.php"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            inserirNovoRegistro()
        }
    }

    //TODO - Criar funcao de inserção de registro na base MySQL no 000webapphost
    fun inserirNovoRegistro() {

        //O que é RequestQueue?
        var requestQueue: RequestQueue = Volley.newRequestQueue(this)

        //Criai ProgressDialog enquanto o processamento em Background é executado
        val progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Salvar Registro")
        progressDialog.setMessage("Por favor, espere...")

        //Exibir ProgressDialog
        progressDialog.show()

        //TODO - Criar objeto StringRequest da Classe Volley e configura-lo
        val stringRequest = object: StringRequest(Request.Method.POST,url, Response.Listener { response ->
            Log.d("Response: ",response.toString())
        }, Response.ErrorListener { error ->
            //TODO - Tratamento de erros - Volley
            Log.d("Error: ", error.localizedMessage)
        }){
            //TODO - Sobrescrita de metodo para adicionar dados ao banco de dados MySQL
            override fun getParams(): MutableMap<String, String> {

                //Configuração de objeto para gravação em base MySQL
                val params = HashMap<String, String>()
                params.put("objetivo", spinnerObjetivo.selectedItem.toString())
                params.put("descricao", editTextDescricao.text.toString())
                params.put("aluno",editTextNomeAluno.text.toString())
                params.put("curso",spinnerCurso.selectedItem.toString())

                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH-mm-ss")
                params.put("data", dateFormat.format(Date()))

                progressDialog.dismiss()
                return params
            }
        }
        requestQueue.add(stringRequest)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.sugestoes ->
                //Iniciar nova Atividade para buscar os dados
                startActivity(Intent(applicationContext, SuggestionListActivity::class.java))
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }
}
