package arrow.instances

import arrow.Kind
import arrow.core.*
import arrow.instance
import arrow.typeclasses.*

@instance(Id::class)
interface IdEqInstance<A> : Eq<Id<A>> {

    fun EQ(): Eq<A>

    override fun eqv(a: Id<A>, b: Id<A>): Boolean =
            EQ().eqv(a.value, b.value)
}

@instance(Id::class)
interface IdFunctorInstance : Functor<ForId> {
    override fun <A, B> map(fa: IdOf<A>, f: kotlin.Function1<A, B>): Id<B> =
            fa.extract().map(f)
}

@instance(Id::class)
interface IdApplicativeInstance : Applicative<ForId> {
    override fun <A, B> ap(fa: IdOf<A>, ff: IdOf<kotlin.Function1<A, B>>): Id<B> =
            fa.extract().ap(ff)

    override fun <A, B> map(fa: IdOf<A>, f: kotlin.Function1<A, B>): Id<B> =
            fa.extract().map(f)

    override fun <A> pure(a: A): Id<A> =
            Id.pure(a)
}

@instance(Id::class)
interface IdMonadInstance : Monad<ForId> {
    override fun <A, B> ap(fa: IdOf<A>, ff: IdOf<kotlin.Function1<A, B>>): Id<B> =
            fa.extract().ap(ff)

    override fun <A, B> flatMap(fa: IdOf<A>, f: kotlin.Function1<A, IdOf<B>>): Id<B> =
            fa.extract().flatMap(f)

    override fun <A, B> tailRecM(a: A, f: kotlin.Function1<A, IdOf<Either<A, B>>>): Id<B> =
            Id.tailRecM(a, f)

    override fun <A, B> map(fa: IdOf<A>, f: kotlin.Function1<A, B>): Id<B> =
            fa.extract().map(f)

    override fun <A> pure(a: A): Id<A> =
            Id.pure(a)
}

@instance(Id::class)
interface IdComonadInstance : Comonad<ForId> {
    override fun <A, B> coflatMap(fa: IdOf<A>, f: kotlin.Function1<IdOf<A>, B>): Id<B> =
            fa.extract().coflatMap(f)

    override fun <A> extractM(fa: IdOf<A>): A =
            fa.extract().extractM()

    override fun <A, B> map(fa: IdOf<A>, f: kotlin.Function1<A, B>): Id<B> =
            fa.extract().map(f)
}

@instance(Id::class)
interface IdBimonadInstance : Bimonad<ForId> {
    override fun <A, B> ap(fa: IdOf<A>, ff: IdOf<kotlin.Function1<A, B>>): Id<B> =
            fa.extract().ap(ff)

    override fun <A, B> flatMap(fa: IdOf<A>, f: kotlin.Function1<A, IdOf<B>>): Id<B> =
            fa.extract().flatMap(f)

    override fun <A, B> tailRecM(a: A, f: kotlin.Function1<A, IdOf<Either<A, B>>>): Id<B> =
            Id.tailRecM(a, f)

    override fun <A, B> map(fa: IdOf<A>, f: kotlin.Function1<A, B>): Id<B> =
            fa.extract().map(f)

    override fun <A> pure(a: A): Id<A> =
            Id.pure(a)

    override fun <A, B> coflatMap(fa: IdOf<A>, f: kotlin.Function1<IdOf<A>, B>): Id<B> =
            fa.extract().coflatMap(f)

    override fun <A> extractM(fa: IdOf<A>): A =
            fa.extract().extractM()
}

@instance(Id::class)
interface IdFoldableInstance : Foldable<ForId> {
    override fun <A, B> foldLeft(fa: IdOf<A>, b: B, f: kotlin.Function2<B, A, B>): B =
            fa.extract().foldLeft(b, f)

    override fun <A, B> foldRight(fa: IdOf<A>, lb: Eval<B>, f: kotlin.Function2<A, Eval<B>, Eval<B>>): Eval<B> =
            fa.extract().foldRight(lb, f)
}

fun <A, G, B> Id<A>.traverse(f: (A) -> Kind<G, B>, GA: Applicative<G>): Kind<G, Id<B>> = GA.map(f(this.extract().value), { Id(it) })

@instance(Id::class)
interface IdTraverseInstance : Traverse<ForId> {
    override fun <A, B> map(fa: IdOf<A>, f: kotlin.Function1<A, B>): Id<B> =
            fa.extract().map(f)

    override fun <G, A, B> traverse(fa: IdOf<A>, f: kotlin.Function1<A, Kind<G, B>>, GA: Applicative<G>): Kind<G, Id<B>> =
            fa.extract().traverse(f, GA)

    override fun <A, B> foldLeft(fa: IdOf<A>, b: B, f: kotlin.Function2<B, A, B>): B =
            fa.extract().foldLeft(b, f)

    override fun <A, B> foldRight(fa: IdOf<A>, lb: Eval<B>, f: kotlin.Function2<A, Eval<B>, Eval<B>>): Eval<B> =
            fa.extract().foldRight(lb, f)
}
