package com.app.easemypost.ui.dop.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.app.easemypost.R

class ExpandableListAdapter1(
    private val context: Context,
    private val driverNames: List<String>,
    private val truckNumbers: List<List<String>>
) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return truckNumbers[groupPosition][childPosition]
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return truckNumbers[groupPosition].size
    }

    override fun getGroup(groupPosition: Int): Any {
        return driverNames[groupPosition]
    }

    override fun getGroupCount(): Int {
        return driverNames.size
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val childView = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.list_item_child, parent, false
        )
        val childText = childView.findViewById<TextView>(R.id.text_truck_number)
        childText.text = getChild(groupPosition, childPosition) as String
        return childView
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val groupView = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.list_item_group, parent, false
        )
        val groupText = groupView.findViewById<TextView>(R.id.text_driver_name)
        groupText.text = getGroup(groupPosition) as String
        return groupView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}