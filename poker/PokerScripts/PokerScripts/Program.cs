using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace PokerScripts
{
    class Program
    {
        static void Main(string[] args)
        {
            CardSorter cardSorter = new CardSorter();
            string path = System.AppDomain.CurrentDomain.BaseDirectory.ToString();

            try
            {
                FileStream streamInput = File.Open(string.Concat(path, "Data/poker-hand-testing.arff"), FileMode.Open);
                cardSorter.SortCardByNumberAndSuit(streamInput);
                streamInput.Close();
            }

            catch (Exception ex)
            {
                Console.WriteLine(ex.Message);
            }
            
        }
    }
}
