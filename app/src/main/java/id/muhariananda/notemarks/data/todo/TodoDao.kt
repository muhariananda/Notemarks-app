package id.muhariananda.notemarks.data.todo

import androidx.room.*
import id.muhariananda.notemarks.data.entities.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo_table ORDER BY isDone ASC")
    fun getTodos(): Flow<List<Todo>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTodo(todo: Todo)

    @Update
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAllTodos()

    @Query("SELECT * FROM todo_table WHERE title LIKE :searchQuery")
    fun searchTodo(searchQuery: String): Flow<List<Todo>>
}