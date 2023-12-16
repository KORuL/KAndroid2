package ru.korul.kandroid2

import androidx.recyclerview.widget.DiffUtil

interface Equatable {
    override fun equals(other: Any?): Boolean
}

fun <T : Equatable> getSimpleDiffUtil(comparator: Comparator<T>) = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem::class != newItem::class)
            return false

        return comparator.compare(oldItem, newItem) == 0
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        if (oldItem::class != newItem::class)
            return false

        return oldItem == newItem
    }
}