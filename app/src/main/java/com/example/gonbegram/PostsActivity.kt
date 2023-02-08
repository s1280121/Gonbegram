package com.example.gonbegram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.firestore.FirebaseFirestore

private const val TAG = "PostsActivity"
class PostsActivity : AppCompatActivity() {

    private lateinit var firestoreDb: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)

        firestoreDb = FirebaseFirestore.getInstance()
        val postsReference = firestoreDb.collection("posts")

        postsReference.addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) {
                Log.e(TAG, "投稿のクエリ時に例外が発生する", e)
                return@addSnapshotListener
            }
            for (document in snapshot.documents) {
                Log.i(TAG, "Document ${document.id}: ${document.data}")
                // document.id → qbjZpePbFlhA3jg4xnnJ
                // document.data → creation_time_ms = 111111.., description = Hipstar Lock...
            }
//            val postList = snapshot.toObjects(Post::class.java)
//            posts.clear()
//            posts.addAll(postList)
//            adapter.notifyDataSetChanged()
//            for (post in postList) {
//                Log.i(TAG, "Post ${post}")
//            }
        }
    }

    //AppBarにアイコンボタンを設置
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_posts, menu)
        return super.onCreateOptionsMenu(menu)
    }

    //AppBarのアイコンボタンを押した時
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_profile) {
            val intent = Intent(this, ProfileActivity::class.java)
//            intent.putExtra(EXTRA_USERNAME, signedInUser?.username)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }
}

//https://cloud.google.com/firestore/docs/query-data/listen?hl=ja
// snapshot = 実際のデータのこと。データの取得はSnapshotに対して行う。
// データの追加、更新、削除はreferenceに対して行う。(add, update, delete) →　インスタンス的なイメージ？
// DocumentはFirebaseのフィールドのこと
// exception エラー処理？ eと同じ？
