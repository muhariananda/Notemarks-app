package id.muhariananda.notemarks.data.todo

import androidx.room.*
import id.muhariananda.notemarks.data.todo.models.ToDo
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM to_do_table ORDER BY isDone ASC")
    fun getTodos(): Flow<List<ToDo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(toDo: ToDo)

    @Update
    suspend fun updateTodo(toDo: ToDo)

    @Delete
    suspend fun deleteTodo(toDo: ToDo)

    @Query("DELETE FROM to_do_table")
    suspend fun deleteAllTodos()
}