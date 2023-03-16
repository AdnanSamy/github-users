package com.list.user.githubuser.service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.list.user.githubuser.dto.GithubUser;
import com.list.user.githubuser.util.HttpRequest;

@Service
public class GithubUserService {
    private static final String GET_URL = "https://api.github.com/users?per_page=100";

    private HttpRequest httpRequest;

    public GithubUserService(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public byte[] getGithubUser() throws IOException, JSONException, DocumentException {
        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer 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");
        headers.put("X-GitHub-Api-Version", "2022-11-28");

        String response = httpRequest.get(headers, GET_URL);

        List<GithubUser> githubUsers = mapper.readValue(response.toString(),
                new com.fasterxml.jackson.core.type.TypeReference<List<GithubUser>>() {
                });

        ByteArrayOutputStream file = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, file);

        document.open();

        PdfPTable table = new PdfPTable(5);
        Stream.of(
                "login",
                "id",
                "node_id",
                "type",
                "site_admin")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });

        for (int i = 0; i < githubUsers.size(); i++) {
            GithubUser githubUser = githubUsers.get(i);
            table.addCell(new PdfPCell(new Phrase(githubUser.getLogin())));
            table.addCell(new PdfPCell(new Phrase(githubUser.getId().toString())));
            table.addCell(new PdfPCell(new Phrase(githubUser.getNode_id())));
            table.addCell(new PdfPCell(new Phrase(githubUser.getType())));
            table.addCell(new PdfPCell(new Phrase(githubUser.getSite_admin().toString())));
        }

        document.add(table);
        document.close();

        return file.toByteArray();
    }
}
