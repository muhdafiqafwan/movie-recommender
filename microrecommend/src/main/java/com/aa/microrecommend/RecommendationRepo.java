package com.aa.microrecommend;

import java.util.List;
import java.sql.ResultSet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
public class RecommendationRepo {
    /*  Since Spring JPA is quite limited for custom query, where I cannot specify custom column names,
        therefore, JDBC Template is use to fetch the recommended movies by using customize query */
    @Autowired
    JdbcTemplate jdbcTemplate;

    // To map the result 
    private RowMapper<Matrix> rowMapper = (ResultSet rs, int rowNum) -> {
        Matrix matrix = new Matrix();
        matrix.setMovieId(rs.getInt(1));
        return matrix;
    };

    // Custom query to find top 5 recommended movies based on the similarity
    public List<Matrix> findAll(int id) {
        String column = "ID_" + id;
        return jdbcTemplate.query("SELECT MOVIE_ID FROM MATRIX WHERE " + column
                + " > 0.5 AND MOVIE_ID <> " + id + " ORDER BY " + column
                + " DESC FETCH FIRST 5 ROWS ONLY", rowMapper);
    }
}