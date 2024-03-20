package tr.edu.ogu.ceng.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ims_language")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column
	private String flag;

	@Column(name = "language_abbr", nullable = false)
	private String languageAbbr;

	@Column(name = "country_abbr", nullable = false)
	private String countryAbbr;

	@Column
	private String country;

}
