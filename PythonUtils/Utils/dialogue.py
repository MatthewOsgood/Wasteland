import sys
import os


def main():
    if len(sys.argv) != 3:
        print("usage: ./dialogue.py input_dir output_dir")
        return
    input_dir = sys.argv[1]
    output_dir = sys.argv[2]
    parse_dir(input_dir, output_dir)


def parse(filename):
    """
    translates the contents of the given file into a JSON formatted string for a .dlg file
    assumes the given file is a properly formatted .txt conversation file
    :param filename: String - the path of the file that will be parsed
    :return String - the JSON formatted translation of this files content"""
    json = "{ \ndialogueLines: [\n"
    with open(filename, "r") as file:
        for line in file:
            index = line.index(":")
            speaker = line[:index]
            dialogue = line[index + 1:].strip()
            dialogue_line = f"\t{{\n\t\tspeaker: {speaker} \n\t\tdialogue: {dialogue}\n\t}}\n"
            json += dialogue_line
        json += "]\n}"
        return json


def parse_dir(input_dir, output):
    """
    translates all .txt files in the given directory into a .dlg dialogue conversation for a SteampunkGame
    if a file already exists in output with the same name the file is overwritten
    :param input_dir: String - the directory to read input files from
    :param output: String - the directory to output files to"""
    for file in os.scandir(input_dir):
        if file.is_file() and file.name[-3:] == "txt":
            json = parse(file.path)
            output_name = file.name[:-3] + "dlg"
            with open(os.path.join(output, output_name), "w") as output_file:
                output_file.write(json)
        else:
            print(f"{file.name} was skipped")


if __name__ == "__main__":
    main()
