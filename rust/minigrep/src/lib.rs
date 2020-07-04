use std::error::Error;
use std::fs;
use std::env;

pub fn run(config: Config) -> Result<(), Box<dyn Error>> {
    let contents = fs::read_to_string(config.filename)?;

    for line in search(&config.query, &contents, config.case_sensitive) {
        println!("{}", line);
    }

    Ok(())
}

pub struct Config {
    pub query: String,
    pub filename: String,
    pub case_sensitive: bool,
}

impl Config {
    pub fn new(args: &[String]) -> Result<Config, &'static str> {
        if args.len() < 3 {
            return Err("not enough arguments");
        }

        let query = args[1].clone();
        let filename = args[2].clone();
        let case_sensitive = env::var("CASE_INSENSITIVE").is_err();

        Ok(Config { query, filename, case_sensitive })
    }
}


pub fn search<'a>(needle: &str, haystack: &'a str, case_sensitive: bool) -> Vec<&'a str> {
    let mut matches = Vec::new();
    for line in haystack.lines() {
        if !case_sensitive {
            if line.to_lowercase().contains(&needle.to_lowercase()) {
                matches.push(line);
            } 
        } else {
            if line.contains(needle) {
                matches.push(line);
            } 
        }
    }
    matches
}

#[cfg(test)]
mod tests {
    use super::search;

    #[test]
    fn test_case_sensitive_search() {
        let results = search("line", "line 1
line 2
ln3", true);
        assert_eq!(results, vec!("line 1", "line 2"));
    }

    #[test]
    fn test_case_insensitive_search() {
        let results = search("LN", "line 1
line 2
ln3", false);
        assert_eq!(results, vec!("ln3"));
    }

}

