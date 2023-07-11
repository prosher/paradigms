package search;

public class BinarySearch {
    // Pre: forall i=0..a.length - 2: a[i] >= a[i + 1]
    // Post: a.length == 0 && R == 0
    //       || x < min(a) && R == a.length
    //       || x >= min(a) && R == min(i=0..a.length-1 : a[i] <= x)
    public static int binSearchIterative(int x, int[] a) {
        int l = -1;
        // l' == -1
        int r = a.length;
        // l' == -1 && r' == a.length
        // a.length == 0 || a.length > 0 && (a.length == 1 || (forall i=0..a.length-2 a[i] >= a[i + 1]))

        // a.length > 0 && (forall i=0..a.length-1 a[i] is integer)
        // && (a.length == 1 || (forall i=0..a.length-2 a[i] >= a[i + 1]))
        // r' == a.length && a.length > 0 -> r' - l' > 1
        // -1 <= l' < r' <= a.length
        // 2 * l' < l' + r' && l' + r' < 2 * r'
        // l' < l' + r' + (l' + r') % 2 && l' + r' + (l' + r') % 2 <= l' + r' + 1 <= r'
        // -1 <= l' < (l' + r') / 2 <= r' <= a.length

        // a.length == 0
        // l' == 1 && r' == 0 && (l' + r') / 2 == 0 && r' - l' <= 1
        // -1 <= l' < (l' + r') / 2 <= r' <= a.length

        // Let a[-1] = 2^31, a[a.length] = -2^31
        // (forall i=-1..a.length-1 a[i] >= a[i + 1])
        // a[-1] >= a[l'] > a[(l' + r') / 2] >= a[r'] >= a[a.length]

        // Inv: a.length > 0 && (forall i=-1..a.length-1 a[i] >= a[i + 1]))
        //      && -1 <= l' < (l' + r') / 2 <= r' <= a.length && a[l'] > x >= a[r']
        while (r - l > 1) {
            // -1 <= l' < (l' + r') / 2 <= r' <= a.length
            // && a[-1] >= a[l'] > a[(l' + r') / 2] >= a[r'] >= a[a.length]
            int m = (l + r) / 2;
            // -1 <= l' < m' <= r' <= a.length
            // && a[-1] >= a[l'] > a[m'] >= a[r'] >= a[a.length]

            // (forall i=-1..a.length-1 a[i] >= a[i + 1]))
            // a[l'] > x >= a[m'] >= a[r'] || a[l'] > a[m'] > x >= a[r']
            if (x < a[m]) {
                // a[l'] > a[m'] > x >= a[r']
                l = m;
                // a[l'] > x >= a[r']
            } else {
                // a[l'] > x >= a[m'] >= a[r']
                r = m;
                // a[l'] > x >= a[r']
            }
            // a[l'] > x >= a[r']
            // -1 <= l' < m' <= r' <= a.length && r' - l' > 1
        }
        // далее продолжим считать m' = (l' + r') / 2
        // a[l'] > x >= a[r']
        // r' - l' <= 1 && l' < m' <= r' && l', m', r' are integer
        // m' - 1 == l' < m' == r'
        // a[m' - 1] > a[m'] == a[r']
        // a[m' - 1] > x >= a[m']
        // m' == a.length || -1 < 0 <= m' <= a.length - 1

        // m' == a.length && a[m' - 1] > x >= a[m']
        // a.length == 0 && m' == 0 || a[m' - 1] == a[a.length - 1] == min(a) && x < min(a)

        // 0 <= m' <= a.length - 1 && a[m' - 1] > x >= a[m']
        // x >= a[a.length] == min(a)
        // (forall i=0..a.length-2 a[i] >= a[i + 1])
        // m' == min(i=0..a.length-1 : a[i] <= x)

        // m' == r'
        return r;
        // a.length == 0 && R == 0
        // || x < min(a) && R == a.length
        // || x >= min(a) && R == min(i=0..a.length-1 : a[i] <= x)
    }


    // Pre: forall i=0..a.length-2 a[i] >= a[i + 1])
    // Post: a.length == 0 && R == 0
    //       || x < min(a) && R == a.length
    //       || x >= min(a) && R == min(i=0..a.length-1 : a[i] <= x)
    public static int binSearchRecursive(int x, int[] a) {
        int l = -1;
        // l' == -1
        int r = a.length;
        // l' == -1 && r' == a.length

        // a.length == 0 || a.length > 0 && (a.length == 1 || (forall i=0..a.length-2 a[i] >= a[i + 1]))

        // a.length > 0 && (forall i=0..a.length-1 a[i] is integer)
        // && (a.length == 1 || (forall i=0..a.length-2 a[i] >= a[i + 1]))
        // r' == a.length && a.length > 0 -> r' - l' > 1
        // -1 <= l' < r' <= a.length
        // 2 * l' < l' + r' && l' + r' < 2 * r'
        // l' < l' + r' + (l' + r') % 2 && l' + r' + (l' + r') % 2 <= l' + r' + 1 <= r'
        // -1 <= l' < (l' + r') / 2 <= r' <= a.length

        // a.length == 0
        // l' == 1 && r' == 0 && (l' + r') / 2 == 0 && r' - l' <= 1
        // -1 <= l' < (l' + r') / 2 <= r' <= a.length

        // Let a[-1] = +inf, a[a.length] = -inf
        // (forall i=-1..a.length-1 a[i] >= a[i + 1])
        // a[-1] >= a[l'] > a[(l' + r') / 2] >= a[r'] >= a[a.length]

        // Pre && a.length >= 0 && (forall i=-1..a.length-1 a[i] >= a[i + 1]))
        //      && -1 <= l' < (l' + r') / 2 <= r' <= a.length && a[l'] > x >= a[r']
        return binSearchRecursiveImpl(x, a, l, r);
        // a.length == 0 && R == 0
        //       || x < min(a) && R == a.length
        //       || x >= min(a) && R == min(i=0..a.length-1 : a[i] <= x)
    }


    // Let: a[-1] = +inf, a[a.length] = -inf
    // Pre: (forall i=-1..a.length-1 a[i] >= a[i + 1])
    //      && -1 <= l' < (l' + r') / 2 <= r' <= a.length && a[l'] > x >= a[r']
    // R - return value
    // Post: a.length == 0 && R == 0
    //       || x < min(a) && R == a.length
    //       || x >= min(a) && R == min(i=0..a.length-1 : a[i] <= x)
    public static int binSearchRecursiveImpl(int x, int[] a, int l, int r) {
        // r' - l' <= 1 || r' - l' > 1
        if (r - l <= 1) {
            // далее продолжим считать m' = (l' + r') / 2
            // a[l'] > x >= a[r']
            // r' - l' <= 1 && l' < m' <= r' && l', m', r' are integer
            // m' - 1 == l' < m' == r'
            // a[m' - 1] > a[m'] == a[r']
            // a[m' - 1] > x >= a[m']
            // m' == a.length || -1 < 0 <= m' <= a.length - 1

            // m' == a.length && a[m' - 1] > x >= a[m']
            // a.length == 0 && m' == 0 || a[m' - 1] == a[a.length - 1] == min(a) && x < min(a)

            // 0 <= m' <= a.length - 1 && a[m' - 1] > x >= a[m']
            // x >= a[a.length] == min(a)
            // (forall i=0..a.length-2 a[i] >= a[i + 1])
            // m' == min(i=0..a.length-1 : a[i] <= x)

            // m' == r'
            return r;
            // a.length == 0 && R == 0
            // || x < min(a) && R == a.length
            // || x >= min(a) && R == min(i=0..a.length-1 : a[i] <= x)
        }
        // r' - l' > 1
        // -1 <= l' < (l' + r') / 2 <= r' <= a.length
        // && a[-1] >= a[l'] > a[(l' + r') / 2] >= a[r'] >= a[a.length]
        int m = (l + r) / 2;
        // -1 <= l' < m' <= r' <= a.length
        // && a[-1] >= a[l'] > a[m'] >= a[r'] >= a[a.length]

        // (forall i=-1..a.length-1 a[i] >= a[i + 1]))
        // a[l'] > x >= a[m'] >= a[r'] || a[l'] > a[m'] > x >= a[r']
        if (x < a[m]) {
            // a[l'] > a[m'] > x >= a[r']
            l = m;
            // a[l'] > x >= a[r']

            // Pre
            return binSearchRecursiveImpl(x, a, l, r);
            // a.length == 0 && R == 0
            //  || min(a) <= x && R == min(i=0..a.length-1 : a[i] <= x)
            //  || min(a) > x && R == a.length
        } else {
            // a[l'] > x >= a[m'] >= a[r']
            r = m;
            // a[l'] > x >= a[r']

            // Pre
            return binSearchRecursiveImpl(x, a, l, r);
            // a.length == 0 && R == 0
            //  || min(a) <= x && R == min(i=0..a.length-1 : a[i] <= x)
            //  || min(a) > x && R == a.length
        }
        // a.length == 0 && R == 0
        //  || min(a) <= x && R == min(i=0..a.length-1 : a[i] <= x)
        //  || min(a) > x && R == a.length
    }


    // Pre: args[] - integers in String format
    //      && (forall i=0..args.length-1 -2^31 <= (int)args[i] <= 2^31 - 1)
    //      && (args.length <= 2 || (forall i=1..args.length-2 (int)args[i] >= (int)args[i + 1]))
    // Post: R, printed in System.out :
    //      args.length == 1 && R == 0
    //    || min(args[i] for i=1..args.length-1) <= args[0] && R == min(i=0..args.length-1 : a[i] <= x)
    //    || min(args[i] for i=1..args.length-1) > args[0] && R == args.length
    public static void main(String[] args) {
        // args[0] - integer in String format && -2^31 <= (int)args[0] <= 2^31 - 1
        int x = Integer.parseInt(args[0]);
        // x - integer && -2^31 <= x <= 2^31 - 1
        int sum = 0;
        // sum' = 0
        int[] array = new int[args.length - 1];
        // array.length = args.length - 1
        // forall i=0..args.length-1 -2^31 <= (int)args[i] <= 2^31 - 1)
        // && forall i=1..args.length-2 (int)args[i] >= (int)args[i + 1]
        for (int i = 0; i < array.length; i++) {
            // (forall j=0..i - 1: -2^31 <= array[j] = (int)args[j + 1] <= 2^31 - 1)
            // args[i + 1] is integer in String format && -2^31 <= (int)args[i + 1] <= 2^31 - 1
            array[i] = Integer.parseInt(args[i + 1]);
            // array[i] is integer && -2^31 <= array[i] <= 2^31 - 1
            // (forall j=0..i: -2^31 <= array[j] = (int)args[j + 1] <= 2^31 - 1)

            // sum' = sum(array[0..i - 1])
            sum += array[i];
            // sum' = sum(array[0..i - 1]) + array[i] = sum(array[j=0..i])
        }
        // forall i=0..args.length-1 -2^31 <= (int)args[i] <= 2^31 - 1)
        // && forall i=1..args.length-2 (int)args[i] >= (int)args[i + 1]
        // sum' = sum(array);

        // sum' % 2 = 0 || sum' % 2 = 1
        if (sum % 2 == 0) {
            // sum' % 2 = 0 && forall i=0..array.length-2 array[i] >= array[i + 1])
            System.out.println(binSearchRecursive(x, array));
            // sum' % 2 = 0 && R, printed in System.out :
            //      args.length == 1 && R == 0
            //    || min(args[i] for i=1..args.length-1) <= args[0] && R == min(i=0..args.length-1 : a[i] <= x)
            //    || min(args[i] for i=1..args.length-1) > args[0] && R == args.length
        } else {
            // sum' % 2 = 1 && ...
            System.out.println(binSearchIterative(x, array));
            // sum' % 2 = 1 && ...
        }
        // R, printed in System.out :
        //      args.length == 1 && R == 0
        //    || min(args[i] for i=1..args.length-1) <= args[0] && R == min(i=0..args.length-1 : a[i] <= x)
        //    || min(args[i] for i=1..args.length-1) > args[0] && R == args.length
    }
}
