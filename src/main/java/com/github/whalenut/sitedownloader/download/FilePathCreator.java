package com.github.whalenut.sitedownloader.download;


import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * The intention is to find some kind of mapping between content-type and file suffixes.
 */
public class FilePathCreator {

    private static final String withSuffix = ".+\\..+";

    //Values copied from the Jetty project, used as fallback if we dont get a filename with an extension.
    //https://github.com/eclipse/jetty.project/blob/jetty-10.0.x/jetty-http/src/main/resources/org/eclipse/jetty/http/mime.properties
    private static final Map<String, String> suffixFallback = new HashMap<>();
    static {
        suffixFallback.put("audio/x-aiff", "aiff");
        suffixFallback.put("application/vnd.android.package-archive", "apk");
        suffixFallback.put("image/apng", "apng");
        suffixFallback.put("video/x.ms.asf", "asf");
        suffixFallback.put("video/x.ms.asx", "asx");
        suffixFallback.put("video/x-msvideo", "avi");
        suffixFallback.put("image/avif", "avif");
        suffixFallback.put("application/x-bcpio", "bcpio");
        suffixFallback.put("application/octet-stream", "bin");
        suffixFallback.put("image/bmp", "bmp");
        suffixFallback.put("application/brotli", "br");
        suffixFallback.put("application/x-cabinet", "cab");
        suffixFallback.put("application/x-netcdf", "cdf");
        suffixFallback.put("application/vnd.ms-htmlhelp", "chm");
        suffixFallback.put("application/java-vm", "class");
        suffixFallback.put("application/x-cpio", "cpio");
        suffixFallback.put("application/mac-compactpro", "cpt");
        suffixFallback.put("application/x-x509-ca-cert", "crt");
        suffixFallback.put("application/x-csh", "csh");
        suffixFallback.put("text/css", "css");
        suffixFallback.put("text/csv", "csv");
        suffixFallback.put("application/x-director", "dir");
        suffixFallback.put("application/x-msdownload", "dll");
        suffixFallback.put("application/msword", "doc");
        suffixFallback.put("application/xml-dtd", "dtd");
        suffixFallback.put("application/x-dvi", "dvi");
        suffixFallback.put("application/vnd.ms-fontobject", "eot");
        suffixFallback.put("text/x-setext", "etx");
        suffixFallback.put("application/andrew-inset", "ez");
        suffixFallback.put("image/gif", "gif");
        suffixFallback.put("application/gzip", "gz");
        suffixFallback.put("application/x-hdf", "hdf");
        suffixFallback.put("application/mac-binhex40", "hqx");
        suffixFallback.put("text/x-component", "htc");
        suffixFallback.put("text/html", "html");
        suffixFallback.put("x-conference/x-cooltalk", "ice");
        suffixFallback.put("image/x-icon", "ico");
        suffixFallback.put("image/ief", "ief");
        suffixFallback.put("model/iges", "iges");
        suffixFallback.put("text/vnd.sun.j2me.app-descriptor", "jad");
        suffixFallback.put("application/java-archive", "jar");
        suffixFallback.put("application/x-java-jnlp-file", "jnlp");
        suffixFallback.put("image/jpeg2000", "jp2");
        suffixFallback.put("image/jpeg", "jpg");
        suffixFallback.put("application/javascript", "js");
        suffixFallback.put("application/json", "json");
        suffixFallback.put("application/x-latex", "latex");
        suffixFallback.put("application/x-troff-man", "man");
        suffixFallback.put("application/mathml+xml", "mathml");
        suffixFallback.put("application/x-troff-me", "me");
        suffixFallback.put("model/mesh", "mesh");
        suffixFallback.put("audio/midi", "midi");
        suffixFallback.put("application/vnd.mif", "mif");
        suffixFallback.put("chemical/x-mdl-molfile", "mol");
        suffixFallback.put("video/x-sgi-movie", "movie");
        suffixFallback.put("audio/mpeg", "mp3");
        suffixFallback.put("video/mp4", "mp4");
        suffixFallback.put("video/mpeg", "mpeg");
        suffixFallback.put("application/x-troff-ms", "ms");
        suffixFallback.put("application/oda", "oda");
        suffixFallback.put("application/vnd.oasis.opendocument.database", "odb");
        suffixFallback.put("application/vnd.oasis.opendocument.chart", "odc");
        suffixFallback.put("application/vnd.oasis.opendocument.formula", "odf");
        suffixFallback.put("application/vnd.oasis.opendocument.graphics", "odg");
        suffixFallback.put("application/vnd.oasis.opendocument.image", "odi");
        suffixFallback.put("application/vnd.oasis.opendocument.text-master", "odm");
        suffixFallback.put("application/vnd.oasis.opendocument.presentation", "odp");
        suffixFallback.put("application/vnd.oasis.opendocument.spreadsheet", "ods");
        suffixFallback.put("application/vnd.oasis.opendocument.text", "odt");
        suffixFallback.put("application/ogg", "ogg");
        suffixFallback.put("application/vnd.oasis.opendocument.chart-template", "otc");
        suffixFallback.put("application/vnd.oasis.opendocument.formula-template", "otf");
        suffixFallback.put("application/vnd.oasis.opendocument.graphics-template", "otg");
        suffixFallback.put("application/vnd.oasis.opendocument.text-web", "oth");
        suffixFallback.put("application/vnd.oasis.opendocument.image-template", "oti");
        suffixFallback.put("application/vnd.oasis.opendocument.presentation-template", "otp");
        suffixFallback.put("application/vnd.oasis.opendocument.spreadsheet-template", "ots");
        suffixFallback.put("application/vnd.oasis.opendocument.text-template", "ott");
        suffixFallback.put("image/x-portable-bitmap", "pbm");
        suffixFallback.put("chemical/x-pdb", "pdb");
        suffixFallback.put("application/pdf", "pdf");
        suffixFallback.put("image/x-portable-graymap", "pgm");
        suffixFallback.put("application/x-chess-pgn", "pgn");
        suffixFallback.put("image/png", "png");
        suffixFallback.put("image/x-portable-anymap", "pnm");
        suffixFallback.put("image/x-portable-pixmap", "ppm");
        suffixFallback.put("application/vnd.ms-powerpoint", "ppt");
        suffixFallback.put("application/postscript", "ps");
        suffixFallback.put("text/x-qml", "qml");
        suffixFallback.put("video/quicktime", "qt");
        suffixFallback.put("audio/x-pn-realaudio", "ra");
        suffixFallback.put("application/x-rar-compressed", "rar");
        suffixFallback.put("image/x-cmu-raster", "ras");
        suffixFallback.put("application/rdf+xml", "rdf");
        suffixFallback.put("image/x-rgb", "rgb");
        suffixFallback.put("application/x-rpm", "rpm");
        suffixFallback.put("application/rtf", "rtf");
        suffixFallback.put("text/richtext", "rtx");
        suffixFallback.put("video/vnd.rn-realvideo", "rv");
        suffixFallback.put("application/java-serialized-object", "ser");
        suffixFallback.put("text/sgml", "sgml");
        suffixFallback.put("application/x-sh", "sh");
        suffixFallback.put("application/x-shar", "shar");
        suffixFallback.put("application/x-stuffit", "sit");
        suffixFallback.put("application/x-koan", "skd");
        suffixFallback.put("application/smil", "smil");
        suffixFallback.put("audio/basic", "snd");
        suffixFallback.put("application/x-futuresplash", "spl");
        suffixFallback.put("application/x-wais-source", "src");
        suffixFallback.put("application/x-sv4cpio", "sv4cpio");
        suffixFallback.put("application/x-sv4crc", "sv4crc");
        suffixFallback.put("image/svg+xml", "svg");
        suffixFallback.put("application/x-shockwave-flash", "swf");
        suffixFallback.put("application/x-tar", "tar");
        suffixFallback.put("application/x-tcl", "tcl");
        suffixFallback.put("application/x-tex", "tex");
        suffixFallback.put("application/x-texinfo", "texinfo");
        suffixFallback.put("application/x-gtar", "tgz");
        suffixFallback.put("image/tiff", "tiff");
        suffixFallback.put("application/x-troff", "tr");
        suffixFallback.put("text/tab-separated-values", "tsv");
        suffixFallback.put("text/plain", "txt");
        suffixFallback.put("application/x-ustar", "ustar");
        suffixFallback.put("application/x-cdlink", "vcd");
        suffixFallback.put("model/vrml", "vrml");
        suffixFallback.put("application/voicexml+xml", "vxml");
        suffixFallback.put("application/wasm", "wasm");
        suffixFallback.put("audio/x-wav", "wav");
        suffixFallback.put("image/vnd.wap.wbmp", "wbmp");
        suffixFallback.put("image/webp", "webp");
        suffixFallback.put("text/vnd.wap.wml", "wml");
        suffixFallback.put("application/vnd.wap.wmlc", "wmlc");
        suffixFallback.put("text/vnd.wap.wmlscript", "wmls");
        suffixFallback.put("application/vnd.wap.wmlscriptc", "wmlsc");
        suffixFallback.put("application/font-woff", "woff");
        suffixFallback.put("font/woff2", "woff2");
        suffixFallback.put("application/vnd.wap.wtls-ca-certificate", "wtls-ca-certificate");
        suffixFallback.put("image/x-xbitmap", "xbm");
        suffixFallback.put("image/xcf", "xcf");
        suffixFallback.put("application/xhtml+xml", "xhtml");
        suffixFallback.put("application/vnd.ms-excel", "xls");
        suffixFallback.put("application/xml", "xml");
        suffixFallback.put("image/x-xpixmap", "xpm");
        suffixFallback.put("application/xslt+xml", "xslt");
        suffixFallback.put("application/vnd.mozilla.xul+xml", "xul");
        suffixFallback.put("image/x-xwindowdump", "xwd");
        suffixFallback.put("chemical/x-xyz", "xyz");
        suffixFallback.put("application/x-xz", "xz");
        suffixFallback.put("application/compress", "z");
        suffixFallback.put("application/zip", "zip");
    }


    public Path build(Path path, String type) {
        String fileName = "index";
        var pathHasAFileName = path.getFileName() != null && !path.getFileName().toString().isBlank() && !path.getFileName().toString().equals("/");

        if (pathHasAFileName) {
            fileName = path.getFileName().toString();
        }

        if (!fileName.matches(withSuffix)) { //No suffix to work with
            String suffix = suffixFallback.getOrDefault(type, "html");
            fileName = String.format("%s.%s", fileName, suffix);
        }

        String parent = "";
        if (path.getParent() != null) {
            parent = path.getParent().toString();
        }
        return Path.of(parent, fileName);
    }
}
