package com.oliveshark.pathworks.framework.grid.util;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Resource scanner util
 */
public class ResourceScanner {

    /**
     * Scan directory in classpath and map results by specified function
     * Can throw NPE if resource not found
     *
     * @param rootDir root for scan
     * @param mapper map function
     * @return return list of file mapped by map function
     */
    public static <R> List<R> scanAndMap(String rootDir, Function<String, R> mapper) {
        return Arrays.stream(getFileList(rootDir))
                .map(f -> String.format("%s/%s", rootDir, f.getName()))
                .map(mapper)
                .collect(Collectors.toList());
    }

    private static File[] getFileList(String rootDir) {
        return new File(ResourceScanner.class.getResource(checkRootSlash(rootDir)).getFile()).listFiles();
    }

    private static String checkRootSlash(String rootDir) {
        return rootDir.startsWith("/") ? rootDir : "/" + rootDir;
    }

}
