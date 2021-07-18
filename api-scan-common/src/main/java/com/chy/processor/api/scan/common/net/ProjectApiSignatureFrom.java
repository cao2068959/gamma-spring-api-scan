package com.chy.processor.api.scan.common.net;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProjectApiSignatureFrom {

    String commitId;
    String appName;
    String ref;
    List<ApiSignatureFrom> apiSignature;

}
