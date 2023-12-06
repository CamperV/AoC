use clap::Parser;
use std::{fs, path::PathBuf};
// use anyhow::{Result, Context};
use regex::Regex;

#[derive(Parser, Debug)]
struct Args {
    input: PathBuf
}

// fn main() -> Result<()> {
fn main() {
    let args = Args::parse();
    println!("Got input {0}", args.input.display());

    let input_content: String = fs::read_to_string(&args.input).unwrap();
        // .with_context(|| format!("Can't read {:?}", args.input.display()))?;

    // part 1: just find digits
    // need to get digits, not full numbers
    // let re = Regex::new(r"([0-9])").unwrap();

    // part 2: spelled-out "one" counts as 1, etc
    let re = Regex::new(r"([0-9]|one|two|three|four|five|six|seven|eight|nine)").unwrap();
    
    // but this is a problem: should be [2, 1, 2, 8, 1]
    // mrttwonetjrt2eightoneqqgvllgpqqbpd, ints: [2, 2, 8, 1]
    // mrttwonetjrt2eightoneqqgvllgpqqbpd -> 21

    let mut running_sum: u32 = 0;

    // need to find all numbers and concat the first and last
    for line in input_content.split("\n") {
        let all_ints: Vec<u32> = re.find_iter(line)
            .map(|cap| {
                match cap.as_str() {
                    "one"   => 1,
                    "two"   => 2,
                    "three" => 3,
                    "four"  => 4,
                    "five"  => 5,
                    "six"   => 6,
                    "seven" => 7,
                    "eight" => 8,
                    "nine"  => 9,
                    _       => cap.as_str().parse().unwrap()
                }
            })
            .collect();

        println!("{}, ints: {:?}", line, all_ints);
        if all_ints.len() > 0 {
            let calibration_num = format!("{}{}", all_ints[0], all_ints.last().unwrap());
            println!("{line} -> {calibration_num}");
            running_sum += calibration_num.parse::<u32>().unwrap();
        }
    }

    println!("total: {}", running_sum);
    // Ok(())
}