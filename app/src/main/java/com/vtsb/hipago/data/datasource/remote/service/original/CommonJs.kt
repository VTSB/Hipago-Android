package com.vtsb.hipago.data.datasource.remote.service.original

import android.util.Log
import com.vtsb.hipago.data.datasource.remote.entity.GalleryFile
import java.util.*
import java.util.regex.Pattern

class CommonJs {

    var galleryblockextension = ".html"
    var galleryblockdir = "galleryblock"
    var nozomiextension = ".nozomi"

    var regex_subdomain_from_url = "/[0-9a-f]/([0-9a-f]{2})/"
    var regex_url_from_url = "//..?\\.hitomi\\.la/"
    var regex_get_path_from_hash = "^.*(..)(.)$"
    var regex_url_from_hash = "\\."

    var pattern_subdomain_from_url = Pattern.compile(regex_subdomain_from_url)

    fun subdomain_from_galleryid(g: Int, number_of_frontends: Int): Char {
        val o = g % number_of_frontends
        return (97 + o).toChar()
    }

    fun subdomain_from_url(url: String, base: String?): String {
        var retval = "b"
        if (base != null) {
            retval = base
        }
        var number_of_frontends = 3
        val b = 16

        // https://stackoverflow.com/questions/8754444/convert-javascript-regular-expression-to-java-syntax
        // regex101.com
        // converted from /\/[0-9a-f]\/([0-9a-f]{2})\//
        val m = pattern_subdomain_from_url.matcher(url)
        if (!m.find()) {
            return "a"
        }
        try {
            var g = Objects.requireNonNull(m.group(1)).toInt(b)
            if (g < 0x30) {
                number_of_frontends = 2
            }
            if (g < 0x09) {
                g = 1
            }
            retval = subdomain_from_galleryid(g, number_of_frontends).toString() + retval
        } catch (exception: NumberFormatException) {
            Log.e(
                CommonJs::class.java.simpleName,
                "Failed To convert " + m.group(1) + " (" + url + ")"
            )
            exception.printStackTrace()
        }
        return retval
    }

    fun url_from_url(url: String, base: String?): String {
        return url.replaceFirst(
            regex_url_from_url.toRegex(),
            "//" + subdomain_from_url(url, base) + ".hitomi.la/"
        )
    }

    fun full_path_from_hash(hash: String): String {
        return if (hash.length < 3) {
            hash
        } else hash.replaceFirst(regex_get_path_from_hash.toRegex(), "$2/$1/$hash")
    }

    fun url_from_hash(galleryid: Long, image: GalleryFile, dir: String?, ext: String?): String {
        var dir = dir
        var ext = ext
        ext = ext
            ?: if (dir != null) {
                dir
            } else {
                val split: List<String> = image.name.split(regex_url_from_hash)
                if (split.isEmpty()) image.name else split[split.size - 1]
            }
        dir = dir ?: "images"
        return "https://a.hitomi.la/" + dir + "/" + full_path_from_hash(image.hash) + "." + ext
    }

    fun url_from_url_from_hash(
        galleryid: Long,
        image: GalleryFile,
        dir: String?,
        ext: String?,
        base: String?
    ): String {
        return url_from_url(url_from_hash(galleryid, image, dir, ext), base)
    }

}