use clap::Parser;
use splitx::split;

#[derive(Parser)]
#[command(author, version, about, long_about = None, after_help="Examples:
  splx --max_size_bytes 4000 -num_header_lines 1 --output_dir ./results ./test.csv
 ")]
struct Cli {
    #[arg(help = "the file to be split", allow_hyphen_values = true)]
    file: String,

    #[arg(short = 'm', long)]
    max_size_bytes: u64,

    #[arg(short = 'n', long)]
    num_header_lines: u8,

    #[arg(short = 'o', long)]
    output_dir: String,
}

fn main() {
    let a = Cli::parse();
    let _ = split(a.file, a.max_size_bytes, a.num_header_lines, a.output_dir);
}
