using System;
using System.IO;

namespace PokerScripts
{
    public class CardSorter
    {
        private StreamWriter writerNumber = new StreamWriter("poker-hand-training-numbersort.arff");
        //private StreamWriter writerSuit = new StreamWriter("poker-hand-training-suitsort.arff");
        private StreamWriter writerNumberAndSuit = new StreamWriter("poker-hand-training-numberandsuitsort.arff");

        public void SortCardByNumberAndSuit (FileStream streamInput)
        {
            StreamReader reader = new StreamReader(streamInput);

            string line = reader.ReadLine();

            while (line != "@data")
            {
                writerNumber.WriteLine(line);
                writerNumberAndSuit.WriteLine(line);
                line = reader.ReadLine();

            }
            writerNumber.WriteLine(line);
            writerNumberAndSuit.WriteLine(line);

            line = reader.ReadLine();

            while (line != null)
            {
                line = ByNumber(line);
                writerNumber.WriteLine();
                line = BySuit(line);
                writerNumberAndSuit.WriteLine();
                line = reader.ReadLine();
            }

            writerNumber.Close();
            writerNumberAndSuit.Close();
        }

        private string ByNumber (string line)
        {
            string result = "";

            var lineSplit = line.Split(',');
            
            for (int i = 9; i >= 1 ; i -= 2)
            {
                for (int j = 1; j < i; j += 2)
                {
                    if (int.Parse(lineSplit[j]) > int.Parse(lineSplit[j + 2]))
                    {
                        string aux1 = lineSplit[j];
                        string aux2 = lineSplit[j - 1];

                        lineSplit[j] = lineSplit[j + 2];
                        lineSplit[j - 1] = lineSplit[j + 1];

                        lineSplit[j + 2] = aux1;
                        lineSplit[j + 1] = aux2;
                    }
                }
            }

            int end = 0;

            foreach (string lines in lineSplit)
            {
                if (end != 10)
                {
                    string.Concat(result, lines, ',');
                    //writerNumber.Write(lines + ",");
                }

                else
                {
                    string.Concat(result, lines);
                    //writerNumber.Write(lines);
                }

                end++;
            }

            return result;
        }

        private string BySuit(string line)
        {
            string result = "";

            var lineSplit = line.Split(',');
            int suit1 = 0, suit2 = 0;

            for (int i = 8; i >= 0; i -= 2)
            {
                for (int j = 0; j < i; j += 2)
                {
                    switch (lineSplit[j])
                    {
                        case "H":
                            suit1 = 1;
                            break;
                        case "S":
                            suit1 = 2;
                            break;
                        case "D":
                            suit1 = 3;
                            break;
                        case "C":
                            suit1 = 4;
                            break;
                        default:
                            break;
                    }

                    switch (lineSplit[j + 2])
                    {
                        case "H":
                            suit2 = 1;
                            break;
                        case "S":
                            suit2 = 2;
                            break;
                        case "D":
                            suit2 = 3;
                            break;
                        case "C":
                            suit2 = 4;
                            break;
                        default:
                            break;
                    }

                
                    if (suit1 > suit2)
                    {
                        string aux1 = lineSplit[j];
                        string aux2 = lineSplit[j + 1];

                        lineSplit[j] = lineSplit[j + 2];
                        lineSplit[j + 1] = lineSplit[j + 3];

                        lineSplit[j + 2] = aux1;
                        lineSplit[j + 3] = aux2;
                    }
                }
            }

            int end = 0;

            foreach (string lines in lineSplit)
            {
                if (end != 10)
                {
                    string.Concat(result, lines, ',');
                    //writerSuit.Write(lines + ",");
                }

                else
                {
                    string.Concat(result, lines);
                    //writerSuit.Write(lines);
                }

                end++;
            }

            return result;
        }

        
    }
}
