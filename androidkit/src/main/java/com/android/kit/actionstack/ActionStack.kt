package com.android.kit.actionstack

import java.util.*

object ActionStack{
    private val undoStack = Stack<()->Unit>()
    private val redoStack = Stack<()->Unit>()
    private var isRedo:Boolean = false

    fun record(action: ()->Unit){
        if(isRedo){
            isRedo = false
            redoStack.push(action)
        }else{
            undoStack.push(action)
        }
    }

    fun undo(){
        if (undoStack.isNotEmpty()) {
            isRedo = true
            undoStack.pop().invoke()
        }
    }

    fun redo(){
        if (redoStack.isNotEmpty()) {
            redoStack.pop().invoke()
        }
    }

    fun clear(){
        undoStack.clear()
        redoStack.clear()
        isRedo = false
    }
}