package com.example.aigul.repository

import models.Todo
import models.User
import repository.Repository
import java.util.*

object NewRepository : Repository {

    val userList = mutableListOf<User>()
    val todoList = mutableListOf<Todo>()
    override suspend fun addUser(email: String,
                                 displayName: String,
                                 passwordHash: String
    ): User? {

        val user = User(userId = UUID.randomUUID().clockSequence(), email = email, displayName = displayName, passwordHash = passwordHash)
        userList.add(user)
        return user
    }

    override suspend fun deleteUser(userId: Int) {
        userList.find {
            it.userId == userId
        }?.apply {
            userList.remove(this)
        }
    }

    override suspend fun findUser(userId: Int): User? {
        return userList.find {
            it.userId == userId
        }
    }

    override suspend fun findUserByEmail(email: String): User? {
        return userList.find {
            it.email == email
        }
    }

    override suspend fun addTodo(userId: Int, todo: String, done: Boolean): Todo? {
        val todo = Todo(UUID.randomUUID().clockSequence(),userId,todo,done)
        todoList.add(
            todo
        )
        return todo
    }

    override suspend fun deleteTodo(userId: Int, todoId: Int) {
        todoList.find {
            it.id == todoId
        }?.apply {
            todoList.remove(this)
        }
    }

    override suspend fun findTodo(userId: Int, todoId: Int): Todo? {
        return todoList.find {
            it.id == todoId
        }
    }

    override suspend fun getTodos(userId: Int): List<Todo> {
        return todoList.filter {
            it.userId == userId
        }
    }

    override suspend fun getTodos(userId: Int, offset: Int, limit: Int): List<Todo> {
        return todoList.filter {
            it.userId == userId
        }
    }
}