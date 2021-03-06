/*
 * Licensed to ElasticSearch and Shay Banon under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. ElasticSearch licenses this
 * file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.elasticsearch.action.admin.indices.template.get;

import com.google.common.collect.Lists;
import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.cluster.metadata.IndexTemplateMetaData;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class GetIndexTemplatesResponse extends ActionResponse {

    private List<IndexTemplateMetaData> indexTemplates;

    GetIndexTemplatesResponse() {
    }

    GetIndexTemplatesResponse(List<IndexTemplateMetaData> indexTemplates) {
        this.indexTemplates = indexTemplates;
    }

    public List<IndexTemplateMetaData> getIndexTemplates() {
        return indexTemplates;
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        super.readFrom(in);
        int size = in.readVInt();
        indexTemplates = Lists.newArrayListWithExpectedSize(size);
        for (int i = 0 ; i < size ; i++) {
            indexTemplates.add(0, IndexTemplateMetaData.Builder.readFrom(in));
        }
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        super.writeTo(out);
        out.writeVInt(indexTemplates.size());
        for (IndexTemplateMetaData indexTemplate : indexTemplates) {
            IndexTemplateMetaData.Builder.writeTo(indexTemplate, out);
        }
    }
}
