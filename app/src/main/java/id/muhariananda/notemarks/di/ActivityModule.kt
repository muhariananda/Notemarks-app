package id.muhariananda.notemarks.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object ActivityModule {

    @Provides
    @ActivityScoped
    fun provideAppCompatActivity(activity: Activity) = activity as AppCompatActivity

    @Provides
    @ActivityScoped
    fun provideFragmentManager(activity: AppCompatActivity) = activity.supportFragmentManager

}