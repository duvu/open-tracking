package me.duvu.tracking.web.rest.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author beou on 8/10/19 07:05
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectionModel implements Serializable {

    private static final long serialVersionUID = 6996567402792245608L;

    private Long[] selections;
}
