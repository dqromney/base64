package com.dqr;

import lombok.Data;

@Data
public class Arguments {
    private Boolean help = Boolean.FALSE;
    private Boolean verbose = Boolean.FALSE;
    private Boolean encode = Boolean.FALSE;
    private Boolean decode = Boolean.FALSE;
    private Boolean chunkMode = Boolean.FALSE;
    private String outDir;
    private String pathFile;
    private String outputExt = ".decoded";
}
