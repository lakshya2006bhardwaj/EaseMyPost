package com.app.easemypost.ui.dop.dashboard.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.app.easemypost.R

class ExpandableListAdapter2(
    private val context: Context,
    private val fleetCompanies: List<String>
) : BaseExpandableListAdapter() {

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return "" // No child data
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return -1
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return 0 // No children for fleet companies
    }

    override fun getGroup(groupPosition: Int): Any {
        return fleetCompanies[groupPosition]
    }

    override fun getGroupCount(): Int {
        return fleetCompanies.size
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
            R.layout.list_item_empty, parent, false
        )
        return childView
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val groupView = convertView ?: LayoutInflater.from(context).inflate(
            R.layout.list_item_fleet_group, parent, false
        )
        val groupText = groupView.findViewById<TextView>(R.id.text_fleet_company)
        groupText.text = getGroup(groupPosition) as String
        return groupView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false // No child data, so it's not selectable
    }
}
