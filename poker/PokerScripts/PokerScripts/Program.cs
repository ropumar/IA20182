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

            try
            {
                FileStream streamInput = File.Open("poker-hand-training.arff", FileMode.Open);
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
