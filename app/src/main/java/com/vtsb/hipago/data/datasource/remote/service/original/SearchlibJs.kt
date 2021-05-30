package com.vtsb.hipago.data.datasource.remote.service.original

import com.vtsb.hipago.data.datasource.remote.service.GalleryDataService
import com.vtsb.hipago.data.datasource.remote.service.converter.ResponseBodyConverter
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import javax.inject.Inject
import kotlin.experimental.and

class SearchlibJs @Inject constructor(
    private val galleryDataService: GalleryDataService,
    private val responseBodyConverter: ResponseBodyConverter
) {

    var regex_sanitize = "[/#]"

    var domain = "ltn.hitomi.la"

    var separator = "-"
    var extension = ".html"
    var galleriesdir = "galleries"
    var index_dir = "tagindex"
    var galleries_index_dir = "galleriesindex"
    var languages_index_dir = "languagesindex"
    var nozomiurl_index_dir = "nozomiurlindex"
    val max_node_size = 464
    val B = 16
    var search_serial = 0
    var search_result_index = -1
    val compressed_nozomi_prefix = "n"

    var tag_index_version: String? = null
    var galleries_index_version: String? = null
    var languages_index_version: String? = null
    var nozomiurl_index_version: String? = null

/*
    fun init(galleryDataService: GalleryDataService) {
        SearchlibJs.galleryDataService = galleryDataService
        Single.fromCallable {
            get_index_version(
                "tagindex"
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(s: String) {
                    tag_index_version = s
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
        Single.fromCallable {
            get_index_version(
                "galleriesindex"
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<String> {
                override fun onSubscribe(d: Disposable) {}
                override fun onSuccess(s: String) {
                    galleries_index_version = s
                }

                override fun onError(e: Throwable) {
                    e.printStackTrace()
                }
            })
    }*/

    fun sanitize(input: String): String {
        return input.replace(regex_sanitize.toRegex(), "")
    }

    // or uint8
    @Throws(NoSuchAlgorithmException::class)
    fun hash_term(term: String): ShortArray {
        val messageDigest = MessageDigest.getInstance("SHA-256")
        val hash = messageDigest.digest(term.toByteArray())
        val result = ShortArray(4)
        result[0] = (hash[0].toShort() and 0xff)
        result[1] = (hash[1].toShort() and 0xff)
        result[2] = (hash[2].toShort() and 0xff)
        result[3] = (hash[3].toShort() and 0xff)
        return result
    }

    @Throws(IOException::class)
    fun get_index_version(name: String): String {
        return galleryDataService.getIndexVersion(name, Date().time)
            .execute().body().let {
            responseBodyConverter.toIndexVersion(it!!)
        }
    }

}
