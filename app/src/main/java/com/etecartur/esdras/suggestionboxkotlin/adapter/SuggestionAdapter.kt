package com.etecartur.esdras.suggestionboxkotlin.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.etecartur.esdras.suggestionboxkotlin.R
import com.etecartur.esdras.suggestionboxkotlin.model.Suggestion
import java.util.*

//TODO - Craiacao de Adapter Class para conversaão e binding de dados na célula personalizada suggestion_card
class SuggestionAdapter(private val context: Context, private val datasource: ArrayList<Suggestion>): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = LayoutInflater.from(context).inflate(R.layout.suggestion_card, null, true)

        val textViewNome = rowView.findViewById(R.id.textViewNome) as TextView
        val textViewDescricao = rowView.findViewById(R.id.textViewDescricao) as TextView
        val textViewObjetivo = rowView.findViewById(R.id.textViewObjetivo) as TextView
        val textViewCurso = rowView.findViewById(R.id.textViewCurso) as TextView
        val textViewData = rowView.findViewById(R.id.textViewData) as TextView
        val imageViewObjetivo = rowView.findViewById(R.id.imageViewObjetivo) as ImageView

        val suggestion = getItem(position) as Suggestion

        textViewNome.text = suggestion.aluno
        textViewObjetivo.text = suggestion.objetivo
        textViewDescricao.text = suggestion.descricao
        textViewCurso.text = suggestion.curso
        Log.d("TempData",suggestion.data.toString())
        textViewData.text = suggestion.data.substring(0,11)

        when(suggestion.objetivo){
            "Sugestão" -> imageViewObjetivo.setImageResource(R.drawable.idea)
            "Elogio" -> imageViewObjetivo.setImageResource(R.drawable.like)
            "Reclamação" -> imageViewObjetivo.setImageResource(R.drawable.brokenheart)
            "Comentário" -> imageViewObjetivo.setImageResource(R.drawable.chat)
            else -> imageViewObjetivo.setImageResource(R.drawable.navigation_empty_icon)
        }

        return rowView
    }

    override fun getItem(position: Int): Any {
        return datasource.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return datasource.size
    }

}