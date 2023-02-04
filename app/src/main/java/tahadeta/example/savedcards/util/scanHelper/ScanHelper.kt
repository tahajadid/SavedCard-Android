package tahadeta.example.savedcards.util.scanHelper

import android.util.Log
import android.util.SparseArray
import androidx.core.util.forEach
import com.google.android.gms.vision.text.TextBlock

object ScanHelper {
    fun removeUnuseful(items: SparseArray<TextBlock>?): ArrayList<String> {
        val arrayList = ArrayList<String>()
        items!!.forEach { i, _ ->
            Log.d("TestDebug", "Value (Helpers): " + items.valueAt(i).value)
            arrayList.add(items.valueAt(i).value)
        }
        return arrayList
    }
}
