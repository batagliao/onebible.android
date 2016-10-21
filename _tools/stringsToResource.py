#!usr/bin/python3

import getopt
import sys
import codecs

def main(argv):
    infile = ''
    outfile = ''
    try:
        opts, args = getopt.getopt(argv, "hi:o:", ["infile=", "outfile="])
    except  getopt.GetoptError:
        print('SCRIPT -i <inputfile> -o <outputfile>')
        sys.exit(1)

    for opt, arg in opts:
        if opt == '-h':
            print('SCRIPT -i <inputfile> -o <outputfile>')
            sys.exit()
        elif opt in ('-i', '-infile'):
            infile = arg
        elif opt in ('-o', '-outfile'):
            outfile = arg

    print('Input file is "', infile)
    print('Output file is "', outfile)

    strfile = open(infile, 'r', encoding='utf-8')
    resfile = open(outfile, 'w', encoding='utf-8')

    resfile.write('<?xml version="1.0" encoding="utf-8"?>\n')
    resfile.write('<resources>\n')

    for line in strfile:
        if line.strip().endswith("[strings]"): continue
        if line.strip() == "": continue
        splited = line.strip().split('=')

        #print(splited[0].strip())
        #print(splited[1].strip())
        sufix = "Summary"
        name = splited[0].strip() + sufix
        value = splited[1].strip().replace('\'', '\\\'').replace('’', '\\\'')
        resfile.write('<string name="{name}">{value}</string>\n'.format(name = name, value = value))

    resfile.write('</resources>\n')

    strfile.close
    resfile.close

if __name__ == '__main__':
    if sys.platform == "win32":
        codecs.register(lambda name: codecs.lookup('utf-8') if name == 'cp65001' else None)
    main(sys.argv[1:])
