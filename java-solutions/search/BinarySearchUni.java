package search;

public class BinarySearchUni {

    // Pre: args[] are integers in String format
    //      && (forall i=0..args.length - 1: -2^31 <= (int)args[i] <= 2^31 - 1)
    //      && exists 0 <= j <= args.length :
    //          (forall i=0..j - 2: (int)args[i] < (int)args[i + 1])
    //          && (forall i=j..args.length - 2: (int)args[i] > (int)args[i + 1])
    // Post: R = min(0 <= j <= args.length : (forall i=0..j - 2: (int)args[i] < (int)args[i + 1])
    //          && (forall i=j..args.length - 2: (int)args[i] > (int)args[i + 1]))
    //       R is printed to System.out
    public static void main(String[] args) {
        int[] array = new int[args.length];
        // args.length = array.length
        int sum = 0;
        // sum' = 0

        // args[] are integers in String format
        //      && (forall i=0..args.length - 1: -2^31 <= (int)args[i] <= 2^31 - 1)
        //      && exists 0 <= j <= args.length :
        //          (forall i=0..j - 2: (int)args[i] < (int)args[i + 1])
        //          && (forall i=j..args.length - 2: (int)args[i] > (int)args[i + 1])
        for (int i = 0; i < args.length; i++) {
            // (forall j=0..i - 1: array[j] = (int)args[j] && -2^31 <= array[j] <= 2^31 - 1)
            // args[i] is integer in String format && -2^31 <= (int)args[i] <= 2^31 - 1
            array[i] = Integer.parseInt(args[i]);
            // array[i] is integer && -2^31 <= array[i] <= 2^31 - 1
            // (forall j=0..i: array[j] = (int)args[j] && -2^31 <= array[j] <= 2^31 - 1)

            // sum' = sum(array[0..i - 1])
            sum += array[i];
            // sum' = sum(array[0..i - 1]) + array[i] = sum(array[j=0..i])
        }
        // (forall i=0..array.length - 1: -2^31 <= array[i] = (int)args[i] <= 2^31 - 1)
        // exists 0 <= j <= array.length :
        //      (forall i=0..j - 2: array[i] < array[i + 1])
        //      && (forall i=j..array.length - 2: array[i] > array[i + 1])
        //
        // sum' = sum(array);

        // sum' % 2 = 0 || sum' % 2 = 1
        if (sum % 2 == 0) {
            // sum' % 2 = 0 && exists 0 <= j <= array.length :
            //      (forall i=0..j - 2: array[i] < array[i + 1])
            //      && (forall i=j..array.length - 2: array[i] > array[i + 1])
            System.out.println(binSearchUniRecursive(array));
            // sum' % 2 = 0 && Post
        } else {
            // sum' % 2 = 1 && ...
            System.out.println(binSearchUniIterative(array));
            // sum' % 2 = 1 && Post
        }
        // Post
    }

    // Pre: exists 0 <= j <= a.length :
    //          (forall i=0..j - 2: a[i] < a[i + 1])
    //          && (forall i=j..a.length - 2: a[i] > a[i + 1])
    // Post: R = min(0 <= j <= a.length : (forall i=0..j - 2: a[i] < a[i + 1])
    //       && (forall i=j..a.length - 2: a[i] > a[i + 1]))
    private static int binSearchUniRecursive(int[] a) {
        int l = 0;
        int r = a.length;
        // l' = 0 && r' = a.length

        // 0 <= l' <= r' <= a.length && exists 0 <= l' <= j <= r' <= a.length :
        //          (forall i=0..j - 2: a[i] < a[i + 1])
        //          && (forall i=j..a.length - 2: a[i] > a[i + 1])
        return binSearchUniRecursiveImpl(a, l, r);
    }

    // Pre: 0 <= l' <= r' <= a.length && exists 0 <= l' <= j <= r' <= a.length :
    //          (forall i=0..j - 2: a[i] < a[i + 1])
    //          && (forall i=j..a.length - 2: a[i] > a[i + 1])
    // Post: R = min(0 <= l' <= j <= r' <= a.length : (forall i=0..j - 2: a[i] < a[i + 1])
    //       && (forall i=j..a.length - 2: a[i] > a[i + 1]))
    private static int binSearchUniRecursiveImpl(int[] a, int l, int r) {
        // l' < r'
        // r' - l' <= 1 || r' - l' > 1
        if (r - l <= 1) {
            // r' - l' <= 1 && a[l'] <= a[j] >= a[r']
            // 0 <= l' < m' <= r' <= a.length
            // r' = l' + 1
            // a[l'] <= a[j] >= a[l' + 1]
            // R = l' = min(0 <= l' <= j <= r' <= a.length : (forall i=0..j - 2: a[i] < a[i + 1])
            //       && (forall i=j..a.length - 2: a[i] > a[i + 1]))
            return l;
            // Post
        }
        // r' - l' > 1
        // 0 <= l' < (l' + r') / 2 <= r' <= a.length
        int m = (l + r) / 2;
        // 0 <= l' < m' <= r' <= a.length
        // 0 <= l' <= m' - 1 < m' <= r' <= a.length
        // exists 0 <= j <= a.length :
        //          (forall i=0..j - 2: a[i] < a[i + 1])
        //          && (forall i=j..a.length - 1: a[i] > a[i + 1])
        // a[m' - 1] > a[m'] || a[m' - 1] <= a[m']
        // a[l'] <= a[j] >= a[m' - 1] > a[m'] >= a[r'] || a[l'] <= a[m' - 1] <= a[m'] <= a[j] >= a[r']
        // 0 <= l' <= j <= r' <= a.length
        if (a[m - 1] > a[m]) {
            // a[l'] <= a[j] >= a[m' - 1] > a[m'] >= a[r']
            // 0 <= l' <= m' - 1 < m' <= r' <= a.length
            r = m;
            // 0 <= l' <= m' <= r' <= a.length
            // a[l'] <= a[j] >= a[m' - 1] > a[r']
            // 0 <= l' <= j <= r' <= a.length
            // 0 <= l' <= r' <= a.length && exists 0 <= l' <= j <= r' <= a.length :
            //          (forall i=0..j - 2: a[i] < a[i + 1])
            //          && (forall i=j..a.length - 2: a[i] > a[i + 1])
            return binSearchUniRecursiveImpl(a, l, r);
            // Post
        } else {
            // a[l'] <= a[m' - 1] <= a[m'] <= a[j] >= a[r']
            l = m;
            // a[l'] <= a[j] >= a[r']
            // 0 <= l' <= j <= r' <= a.length
            // 0 <= l' <= r' <= a.length && exists 0 <= l' <= j <= r' <= a.length :
            //          (forall i=0..j - 2: a[i] < a[i + 1])
            //          && (forall i=j..a.length - 2: a[i] > a[i + 1])
            return binSearchUniRecursiveImpl(a, l, r);
            // Post
        }
        // Post
    }


    // Pre: exists 0 <= j <= a.length :
    //          (forall i=0..j - 2: a[i] < a[i + 1])
    //          && (forall i=j..a.length - 2: a[i] > a[i + 1])
    // Post: R = min(0 <= j <= a.length : (forall i=0..j - 2: a[i] < a[i + 1])
    //       && (forall i=j..a.length - 2: a[i] > a[i + 1]))
    private static int binSearchUniIterative(int[] a) {
        int l = 0;
        // l' = 0
        int r = a.length;
        // l' = 0 && r' = a.length

        // a.length >= 0
        // a.length <= 1 || a.length > 1

        // a.length > 1 && r' = a.length > 1
        // r' - l' > 1
        // 0 <= l' < r' <= a.length
        // 2 * l' < l' + r' && l' + r' < 2 * r'
        // l' < (l' + r') / 2 + (l' + r') % 2 && (l' + r') / 2 + (l' + r') % 2 <= (l' + r') / 2 + 1 <= r'
        // 0 <= l' < (l' + r') / 2 <= r' <= a.length

        // a.length <= 1
        // l' = 0 && r' <= 1 && (l' + r') / 2 = 0 && r' - l' <= 1
        // l' = 0 = min(j : (forall i=0..j - 2: a[i] < a[i + 1])
        // && (forall i=j..a.length - 2: a[i] > a[i + 1]))
        // далее рассматривается только случай a.length > 1

        // Inv: 0 <= l' <= r' <= a.length && exists 0 <= l' <= j <= r' <= a.length :
        //          (forall i=0..j - 2: a[i] < a[i + 1])
        //          && (forall i=j..a.length - 1: a[i] > a[i + 1])
        //      && 0 <= l' < (l' + r') / 2 <= r' <= a.length
        //      && a[l'] <= a[j] >= a[r']
        while (r - l > 1) {
            // r' - l' > 1
            // 0 <= l' < (l' + r') / 2 <= r' <= a.length
            int m = (l + r) / 2;
            // 0 <= l' <= m' <= r' <= a.length
            // 0 <= l' <= m' - 1 < m' <= r' <= a.length
            // exists 0 <= j <= a.length :
            //          (forall i=0..j - 2: a[i] < a[i + 1])
            //          && (forall i=j..a.length - 1: a[i] > a[i + 1])
            // a[m' - 1] > a[m'] || a[m' - 1] <= a[m']
            // a[l'] <= a[j] >= a[m' - 1] > a[m'] >= a[r'] || a[l'] <= a[m' - 1] <= a[m'] <= a[j] >= a[r']
            if (a[m - 1] > a[m]) {
                // a[l'] <= a[j] >= a[m' - 1] > a[m'] >= a[r']
                r = m;
                // 0 <= l' <= m' <= r' <= a.length
                // a[l'] <= a[j] >= a[m' - 1] > a[r']
            } else {
                // a[l'] <= a[m' - 1] <= a[m'] <= a[j] >= a[r']
                l = m;
                // 0 <= l' <= m' <= r' <= a.length
                // a[l'] <= a[j] >= a[r']
            }
            // 0 <= l' <= m' <= r' <= a.length
            // a[l'] <= a[j] >= a[r']
        }
        // r' - l' <= 1 && a[l'] <= a[j] >= a[r']
        // 0 <= l' <= m' <= r' <= a.length
        // r' = l' + 1
        // a[l'] <= a[j] >= a[l' + 1]
        // l' = min(j : (forall i=0..j - 2: a[i] < a[i + 1])
        //       && (forall i=j..a.length - 2: a[i] > a[i + 1]))
        return l;
        // R = l' = min(j : (forall i=0..j - 2: a[i] < a[i + 1])
        //       && (forall i=j..a.length - 2: a[i] > a[i + 1]))
    }
}
